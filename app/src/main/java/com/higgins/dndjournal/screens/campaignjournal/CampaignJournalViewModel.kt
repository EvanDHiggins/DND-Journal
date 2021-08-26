package com.higgins.dndjournal.screens.campaignjournal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.higgins.dndjournal.db.journalentry.JournalEntryDao
import com.higgins.dndjournal.db.journaltype.JournalDao
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
}