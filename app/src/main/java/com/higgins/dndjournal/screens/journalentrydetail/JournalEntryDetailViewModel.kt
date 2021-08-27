package com.higgins.dndjournal.screens.journalentrydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.higgins.dndjournal.db.journalentry.JournalEntry
import com.higgins.dndjournal.db.journalentry.JournalEntryDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JournalEntryDetailViewModel @Inject constructor(private val journalEntryDao: JournalEntryDao) :
    ViewModel() {
    fun loadJournalEntry(journalEntryId: Int): LiveData<JournalEntry> =
        journalEntryDao.getJournalEntry(journalEntryId)
}