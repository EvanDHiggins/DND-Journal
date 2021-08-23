package com.higgins.dndjournal.db.campaign

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CampaignDao {

    @Query("SELECT * FROM campaign")
    fun getAll(): LiveData<List<Campaign>>

    // DO NOT USE.
    // This is basically only around for debug database population. It's error-prone to
    // assume that a campaign's name is unique.
    @Query("SELECT * FROM campaign WHERE name = :name LIMIT 1")
    suspend fun getByName(name: String): Campaign

    @Insert
    suspend fun insertAll(vararg campaigns: Campaign)

    @Query("DELETE FROM campaign")
    suspend fun deleteAll()
}