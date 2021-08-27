package com.higgins.dndjournal.db.journaltype

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface JournalDao {
    @Insert
    suspend fun insertAll(vararg journal: Journal)

    @Query("SELECT * FROM journal WHERE campaignId = :campaignId")
    suspend fun getJournalsForCampaignAsync(campaignId: Int): List<Journal>

    @Query("SELECT * FROM journal WHERE campaignId = :campaignId")
    fun getJournalsForCampaign(campaignId: Int): Flow<List<Journal>>
}