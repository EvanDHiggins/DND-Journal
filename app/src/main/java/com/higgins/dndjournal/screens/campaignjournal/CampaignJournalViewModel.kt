package com.higgins.dndjournal.screens.campaignjournal

import androidx.lifecycle.ViewModel
import com.higgins.dndjournal.db.quest.QuestDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import com.higgins.dndjournal.db.location.DndLocationDao
import com.higgins.dndjournal.util.toggle

@HiltViewModel
class CampaignJournalViewModel @Inject constructor(
    private val questDao: QuestDao,
    private val locationDao: DndLocationDao
) : ViewModel() {
    fun observableQuests(campaignId: Int) = questDao.getQuestsForCampaign(campaignId)
    fun observableLocations(campaignId: Int) = locationDao.getLocationsForCampaign(campaignId)

    private val _expandedJournals = MutableLiveData<Set<JournalType>>(setOf())
    val expandedJournals = _expandedJournals

    fun toggleCategorySelection(journalType: JournalType) {
        _expandedJournals.value = _expandedJournals.value?.toggle(journalType) ?: throw
        RuntimeException(
            """Did not find existing set for CampaignNournalViewModel::expandedCategories.
                | This should be initialized to an empty set and never null.""".trimMargin()
        )
    }
}