package com.higgins.dndjournal.screens.journalentrydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.higgins.dndjournal.db.entrybullet.EntryBullet
import com.higgins.dndjournal.db.entrybullet.EntryBulletDao
import com.higgins.dndjournal.db.journalentry.JournalEntry
import com.higgins.dndjournal.db.journalentry.JournalEntryDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JournalEntryDetailViewModel @Inject constructor(
    private val journalEntryDao: JournalEntryDao,
    private val entryBulletDao: EntryBulletDao
) :
    ViewModel() {
    fun loadJournalEntry(journalEntryId: Int): LiveData<JournalEntry> =
        journalEntryDao.getJournalEntry(journalEntryId)

    fun getBulletsForEntry(journalEntryId: Int) =
        entryBulletDao.getBulletsForJournalEntry(journalEntryId)
}