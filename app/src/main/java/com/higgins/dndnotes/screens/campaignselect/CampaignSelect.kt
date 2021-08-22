package com.higgins.dndnotes.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.higgins.dndnotes.screens.campaignselect.CampaignSelectViewModel

// TODO: Add the ability to create new campaigns with the FAB.
@ExperimentalMaterialApi
@Composable
fun CampaignSelect(
    onSelectCampaign: (campaignId: Int) -> Unit,
    campaignSelectViewModel: CampaignSelectViewModel = hiltViewModel()) {
    val campaigns by campaignSelectViewModel.observableCampaigns.observeAsState(listOf())
    LazyColumn {
        items(campaigns) {
            ExpandableListCard(it.name, onClick = { onSelectCampaign(it.id) })
        }
    }
}