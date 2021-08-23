package com.higgins.dndjournal.db.npcs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NpcDao {
    @Insert
    suspend fun insertAll(vararg npc: DndNpc)

    @Query("SELECT * FROM DndNpc WHERE campaignId = :campaignId")
    fun getNpcsForCampaign(campaignId: Int): Flow<List<DndNpc>>
}
