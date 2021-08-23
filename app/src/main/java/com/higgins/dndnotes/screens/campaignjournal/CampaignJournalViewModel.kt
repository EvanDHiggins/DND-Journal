package com.higgins.dndnotes.screens.campaignjournal

import androidx.lifecycle.ViewModel
import com.higgins.dndnotes.db.quest.QuestDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.MutableLiveData
import com.higgins.dndnotes.util.toggle

@HiltViewModel
class CampaignJournalViewModel @Inject constructor(private val questDao: QuestDao) : ViewModel() {
    fun observableQuests(campaignId: Int) = questDao.getQuestsForCampaign(campaignId)

    private val _expandedCategories = MutableLiveData<Set<Int>>(setOf())
    val expandedCategories = _expandedCategories

    fun toggleCategorySelection(categoryName: Int) {
        _expandedCategories.value = _expandedCategories.value?.toggle(categoryName) ?: throw
        RuntimeException(
            """Did not find existing set for CampaignNournalViewModel::expandedCategories.
                | This should be initialized to an empty set and never null.""".trimMargin()
        )
    }
}