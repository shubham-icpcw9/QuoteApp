package com.example.quoteapp.Paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.quoteapp.DB.QuoteDB
import com.example.quoteapp.Models.QuoteRemoteKeys
import com.example.quoteapp.Models.Result
import com.example.quoteapp.Retrofit.QuoteApi

@ExperimentalPagingApi
class QuoteRemoteMediator(
    private val quoteApi: QuoteApi,
    private val quoteDB: QuoteDB
) : RemoteMediator<Int, Result>(){

    private val quoteDao = quoteDB.getQuoteDao()
    private val quoteRemoteKeysDao = quoteDB.getRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Result>): MediatorResult {
        return try {

            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                }
            }

            val response = quoteApi.getQuotes(currentPage)
            val endOfPaginationReached = response.totalPages == currentPage

            val prevPage = if(currentPage == 1) null else currentPage -1
            val nextPage = if(endOfPaginationReached) null else currentPage + 1

            quoteDB.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    quoteDao.deleteQuotes()
                    quoteRemoteKeysDao.deleteAllRemoteKeys()
                }

                quoteDao.insertQuote(response.results)
                val keys = response.results.map { quote ->
                    QuoteRemoteKeys(
                        id = quote._id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                quoteRemoteKeysDao.insertAllRemoteKeys(keys)
            }
            MediatorResult.Success(endOfPaginationReached)
        }
        catch (e: Exception){
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Result>
    ): QuoteRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?._id?.let { id ->
                quoteRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Result>
    ): QuoteRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { quote ->
                quoteRemoteKeysDao.getRemoteKeys(id = quote._id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Result>
    ): QuoteRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { quote ->
                quoteRemoteKeysDao.getRemoteKeys(id = quote._id)
            }
    }

}