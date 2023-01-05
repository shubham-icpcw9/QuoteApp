package com.example.quoteapp.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quoteapp.Models.QuoteRemoteKeys

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM QuoteRemoteKeys WHERE id = :id")
    suspend fun getRemoteKeys(id : String) : QuoteRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(remoteKeys : List<QuoteRemoteKeys>)

    @Query("DELETE FROM QuoteRemoteKeys")
    suspend fun deleteAllRemoteKeys()
}