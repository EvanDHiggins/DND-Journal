package com.higgins.dndnotes.db.quest

import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestDao {

    @Insert
    suspend fun insertAll(vararg quest: Quest)

    @Query("SELECT * FROM quest WHERE campaignId = :campaignId")
    fun getQuestsForCampaign(campaignId: Int): Flow<List<Quest>>
}