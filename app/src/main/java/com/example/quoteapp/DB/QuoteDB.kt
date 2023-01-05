package com.example.quoteapp.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quoteapp.Models.QuoteRemoteKeys
import com.example.quoteapp.Models.Result

@Database(entities = [Result::class, QuoteRemoteKeys::class], version = 1)
abstract class QuoteDB : RoomDatabase(){

    abstract fun getQuoteDao() : QuoteDao

    abstract fun getRemoteKeysDao() : RemoteKeysDao
}