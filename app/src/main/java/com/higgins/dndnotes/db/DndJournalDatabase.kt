package com.higgins.dndnotes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.higgins.dndnotes.db.campaign.Campaign
import com.higgins.dndnotes.db.campaign.CampaignDao
import com.higgins.dndnotes.db.quest.Quest
import com.higgins.dndnotes.db.quest.QuestDao

@Database(entities = [Campaign::class, Quest::class], version = 3)
abstract class DndJournalDatabase : RoomDatabase() {
    abstract fun campaignDao(): CampaignDao
    abstract fun questDao(): QuestDao
}