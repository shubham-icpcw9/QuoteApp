package com.example.quoteapp.Repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.quoteapp.DB.QuoteDB
import com.example.quoteapp.Paging.QuoteRemoteMediator
import com.example.quoteapp.Retrofit.QuoteApi
import javax.inject.Inject

@ExperimentalPagingApi
class QuoteRepository @Inject constructor(private val quoteApi: QuoteApi,
                                          private val quoteDatabase: QuoteDB
) {

    fun getQuotes() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        remoteMediator = QuoteRemoteMediator(quoteApi, quoteDatabase),
        pagingSourceFactory = { quoteDatabase.getQuoteDao().getQuotes() }
    ).liveData
}