package com.higgins.dndjournal.screens.campaignjournal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.higgins.dndjournal.db.journalentry.JournalEntry
import com.higgins.dndjournal.db.journalentry.JournalEntryDao
import com.higgins.dndjournal.db.journaltype.Journal
import com.higgins.dndjournal.db.journaltype.JournalDao
import com.higgins.dndjournal.state.ValueCreationStateWithContext
import com.higgins.dndjournal.util.toggle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CampaignJournalViewModel @Inject constructor(
    private val journalDao: JournalDao,
    private val journalEntryDao: JournalEntryDao,
) : ViewModel() {
    fun observableJournals(campaignId: Int) = journalDao.getJournalsForCampaign(campaignId)
    fun entriesForJournal(journalId: Int) = journalEntryDao.getEntriesForJournal(journalId)

    private val _expandedJournalIds = MutableLiveData<Set<Int>>(setOf())
    val expandedJournalIds = _expandedJournalIds

    fun toggleCategorySelection(journalId: Int) {
        _expandedJournalIds.value = _expandedJournalIds.value?.toggle(journalId) ?: throw
        RuntimeException(
            """Did not find existing set for CampaignNournalViewModel::expandedCategories.
                | This should be initialized to an empty set and never null.""".trimMargin()
        )
    }

    fun collapseAllExcept(journalId: Int) {
        _expandedJournalIds.value = setOf(journalId)
    }

    val journalCreation =
        ValueCreationStateWithContext<String, Int>(viewModelScope) { journalName, campaignId ->
            journalDao.insertAll(Journal(campaignId, journalName))
        }

    val journalEntryCreation =
        ValueCreationStateWithContext<String, Int>(viewModelScope) { entryName, journalId ->
            journalEntryDao.insertAll(JournalEntry(journalId, entryName))
        }
}
