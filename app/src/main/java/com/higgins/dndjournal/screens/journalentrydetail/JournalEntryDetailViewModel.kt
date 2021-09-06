package com.higgins.dndjournal.screens.journalentrydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.higgins.dndjournal.db.entrybullet.EntryBullet
import com.higgins.dndjournal.db.entrybullet.EntryBulletDao
import com.higgins.dndjournal.db.journalentry.JournalEntry
import com.higgins.dndjournal.db.journalentry.JournalEntryDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    fun newBulletForJournalEntry(journalEntryId: Int) {
        viewModelScope.launch {
            entryBulletDao.insertAll(EntryBullet(journalEntryId, ""))
        }
    }

    fun deleteEntry(entryBulletId: Int) {
        viewModelScope.launch {
            entryBulletDao.deleteById(entryBulletId)
        }
    }

    fun persistBulletContent(entryBulletId: Int, content: String) {
        viewModelScope.launch {
            entryBulletDao.updateContent(entryBulletId, content)
        }
    }
}