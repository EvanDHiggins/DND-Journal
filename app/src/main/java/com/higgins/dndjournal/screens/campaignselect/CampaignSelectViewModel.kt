package com.higgins.dndjournal.screens.campaignselect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.higgins.dndjournal.db.campaign.Campaign
import com.higgins.dndjournal.db.campaign.CampaignDao
import com.higgins.dndjournal.state.ValueCreationState
import com.higgins.dndjournal.util.toggle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CampaignSelectViewModel @Inject constructor(private val campaignDao: CampaignDao) :
    ViewModel() {
    val observableCampaigns = campaignDao.getAll()


    val campaignCreationState = ValueCreationState<String>(viewModelScope) {
        campaignDao.insertAll(Campaign(it))
    }

    private val _selectedForDeletion = MutableLiveData<Set<Int>>(setOf())
    val selectedForDeletion: LiveData<Set<Int>> = _selectedForDeletion

    fun selectForDeletion(campaignId: Int) {
        _selectedForDeletion.value = _selectedForDeletion.value?.toggle(campaignId)
    }

    fun deleteSelectedCampaigns() {
        val toDelete = _selectedForDeletion.value?.toSet() ?: setOf()
        _selectedForDeletion.value = setOf()
        viewModelScope.launch {
            campaignDao.deleteCampaignsWithIds(toDelete)
        }
    }
}