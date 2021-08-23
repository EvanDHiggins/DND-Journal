package com.higgins.dndjournal.composables

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.higgins.dndjournal.screens.campaignselect.CampaignSelectViewModel

// TODO: Add the ability to create new campaigns with the FAB.
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun CampaignSelect(
    onSelectCampaign: (campaignId: Int) -> Unit,
    campaignSelectViewModel: CampaignSelectViewModel = hiltViewModel()
) {
    val campaigns by campaignSelectViewModel.observableCampaigns.observeAsState(listOf())
    LazyColumn(modifier = Modifier
        .padding(horizontal = 0.dp, vertical = 12.dp)
        .fillMaxHeight(1f)) {
        items(campaigns) {
            ListCard(it.name, onClick = { onSelectCampaign(it.id) })
        }
    }
}