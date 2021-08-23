package com.higgins.dndjournal.screens.campaignjournal

import androidx.lifecycle.ViewModel
import com.higgins.dndjournal.db.quest.QuestDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import com.higgins.dndjournal.util.toggle

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