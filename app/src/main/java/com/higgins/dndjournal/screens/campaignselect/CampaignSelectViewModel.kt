package com.higgins.dndjournal.screens.campaignselect

import androidx.lifecycle.ViewModel
import com.higgins.dndjournal.db.campaign.CampaignDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CampaignSelectViewModel @Inject constructor(campaignDao: CampaignDao) : ViewModel() {
    val observableCampaigns = campaignDao.getAll()
}