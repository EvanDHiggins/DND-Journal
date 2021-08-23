package com.higgins.dndjournal.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.higgins.dndjournal.db.campaign.Campaign
import com.higgins.dndjournal.db.campaign.CampaignDao
import com.higgins.dndjournal.db.quest.Quest
import com.higgins.dndjournal.db.quest.QuestDao

@Database(entities = [Campaign::class, Quest::class], version = 3)
abstract class DndJournalDatabase : RoomDatabase() {
    abstract fun campaignDao(): CampaignDao
    abstract fun questDao(): QuestDao
}