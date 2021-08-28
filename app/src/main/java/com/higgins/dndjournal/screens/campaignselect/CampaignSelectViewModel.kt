package com.higgins.dndjournal.screens.campaignselect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.higgins.dndjournal.db.campaign.Campaign
import com.higgins.dndjournal.db.campaign.CampaignDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CampaignSelectViewModel @Inject constructor(private val campaignDao: CampaignDao) :
    ViewModel() {
    val observableCampaigns = campaignDao.getAll()

    private val _enteringNewCampaign = MutableLiveData<Boolean>(false)
    val enteringNewCampaign: LiveData<Boolean> = _enteringNewCampaign


    fun beginEnterNewCampaignState() {
        _enteringNewCampaign.value = true
    }

    fun finishEnteringNewCampaign(campaign: String) {
        viewModelScope.launch {
            campaignDao.insertAll(Campaign(campaign))
            _enteringNewCampaign.value = false
        }
    }
}