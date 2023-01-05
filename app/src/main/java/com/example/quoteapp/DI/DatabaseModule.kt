package com.example.quoteapp.DI

import android.content.Context
import androidx.room.Room
import com.example.quoteapp.DB.QuoteDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDB(@ApplicationContext context: Context) : QuoteDB {
        return Room.databaseBuilder(context, QuoteDB::class.java, "QuoteDB").build()
    }
}