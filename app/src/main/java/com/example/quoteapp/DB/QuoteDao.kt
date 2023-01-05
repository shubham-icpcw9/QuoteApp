package com.example.quoteapp.DB

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quoteapp.Models.Result

@Dao
interface QuoteDao {

    @Query("SELECT * FROM Quote")
    fun getQuotes() : PagingSource<Int, Result>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quotes : List<Result>)

    @Query("DELETE FROM Quote")
    suspend fun deleteQuotes()
}