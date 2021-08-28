package com.higgins.dndjournal.screens.campaignjournal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.higgins.dndjournal.db.journalentry.JournalEntryDao
import com.higgins.dndjournal.db.journaltype.Journal
import com.higgins.dndjournal.db.journaltype.JournalDao
import com.higgins.dndjournal.util.toggle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
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


    private val _creatingNewJournal = MutableLiveData<Boolean>(false)
    val creatingNewJournal: LiveData<Boolean> = _creatingNewJournal
    private var campaignIdForNewJournal: Int? = null

    fun beginCreatingJournal(campaignId: Int) {
        campaignIdForNewJournal = campaignId
        _creatingNewJournal.value = true
    }

    fun finishCreatingJournal(journalName: String) {
        if (campaignIdForNewJournal == null) {
            throw IllegalStateException(
                "Tried to create a new journal without a campaignId to back it.")
        }
        viewModelScope.launch {
            journalDao.insertAll(Journal(campaignIdForNewJournal!!, journalName))
            _creatingNewJournal.value = false
        }
    }

    fun cancelCreateJournal() {
        _creatingNewJournal.value = false
    }
}