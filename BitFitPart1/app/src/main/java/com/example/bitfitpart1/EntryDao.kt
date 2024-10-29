package com.example.bitfitpart1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {
    @Query("SELECT * FROM entry_table")
    fun getAll(): Flow<List<EntryEntity>>

    @Query("SELECT * FROM entry_table")
    suspend fun getAllEntries(): List<EntryEntity> // Add this function

    @Insert
    suspend fun insert(entry: EntryEntity)

    @Query("DELETE FROM entry_table")
    suspend fun deleteAll()
}
