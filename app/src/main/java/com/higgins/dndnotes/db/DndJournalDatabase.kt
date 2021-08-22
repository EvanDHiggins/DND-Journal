package com.higgins.dndnotes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.higgins.dndnotes.db.campaign.Campaign
import com.higgins.dndnotes.db.campaign.CampaignDao

@Database(entities = [Campaign::class], version = 2)
abstract class DndJournalDatabase : RoomDatabase() {
    abstract fun campaignDao(): CampaignDao
}