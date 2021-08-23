package com.higgins.dndjournal.db.tags

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {
    @Insert
    suspend fun insertAll(vararg tag: DndTag)

    @Query("SELECT * FROM DndTag WHERE campaignId = :campaignId")
    fun getTagsForCampaign(campaignId: Int): Flow<List<DndTag>>
}