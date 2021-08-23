package com.higgins.dndjournal.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.higgins.dndjournal.db.campaign.Campaign
import com.higgins.dndjournal.db.campaign.CampaignDao
import com.higgins.dndjournal.db.location.DndLocation
import com.higgins.dndjournal.db.location.DndLocationDao
import com.higgins.dndjournal.db.npcs.DndNpc
import com.higgins.dndjournal.db.npcs.NpcDao
import com.higgins.dndjournal.db.quest.Quest
import com.higgins.dndjournal.db.quest.QuestDao
import com.higgins.dndjournal.db.tags.DndTag
import com.higgins.dndjournal.db.tags.TagDao

@Database(
    entities = [Campaign::class, Quest::class, DndLocation::class, DndNpc::class, DndTag::class],
    version = 5
)
abstract class DndJournalDatabase : RoomDatabase() {
    abstract fun campaignDao(): CampaignDao
    abstract fun questDao(): QuestDao
    abstract fun locationDao(): DndLocationDao
    abstract fun npcDao(): NpcDao
    abstract fun tagDao(): TagDao
}