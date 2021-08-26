package com.higgins.dndjournal.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.higgins.dndjournal.db.campaign.Campaign
import com.higgins.dndjournal.db.campaign.CampaignDao
import com.higgins.dndjournal.db.journalentry.JournalEntry
import com.higgins.dndjournal.db.journalentry.JournalEntryDao
import com.higgins.dndjournal.db.journaltype.Journal
import com.higgins.dndjournal.db.journaltype.JournalDao
import com.higgins.dndjournal.db.tags.DndTag
import com.higgins.dndjournal.db.tags.TagDao

@Database(
    entities =
    [
        Campaign::class,
        Journal::class,
        JournalEntry::class,
        DndTag::class
    ],
    version = 8
)
abstract class DndJournalDatabase : RoomDatabase() {
    abstract fun campaignDao(): CampaignDao
    abstract fun journalDao(): JournalDao
    abstract fun journalEntryDao(): JournalEntryDao
    abstract fun tagDao(): TagDao
}