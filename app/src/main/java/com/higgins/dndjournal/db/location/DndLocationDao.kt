package com.higgins.dndjournal.db.location

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DndLocationDao {
    @Insert
    suspend fun insertAll(vararg location: DndLocation)

    @Query("SELECT * FROM DndLocation WHERE campaignId = :campaignId")
    fun getLocationsForCampaign(campaignId: Int): Flow<List<DndLocation>>
}