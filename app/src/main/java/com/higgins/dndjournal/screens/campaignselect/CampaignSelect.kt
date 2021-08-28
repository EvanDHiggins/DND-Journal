package com.higgins.dndjournal.screens.campaignselect

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.higgins.dndjournal.composables.EditTextListCard
import com.higgins.dndjournal.composables.ListCard
import com.higgins.dndjournal.state.AppBarState


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun CampaignSelect(
    onSelectCampaign: (campaignId: Int) -> Unit,
    appBarState: AppBarState,
    campaignSelectViewModel: CampaignSelectViewModel = hiltViewModel()
) {
    appBarState.setAppBarAddAction {
        campaignSelectViewModel.beginEnterNewCampaignState()
    }
    val campaigns by campaignSelectViewModel.observableCampaigns.observeAsState(listOf())
    val enteringNewCampaign by campaignSelectViewModel.enteringNewCampaign
        .observeAsState(false)
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 0.dp, vertical = 12.dp)
            .fillMaxHeight(1f)
    ) {
        if (enteringNewCampaign) {
            item {
                EditTextListCard(onDone = {
                    campaignSelectViewModel.finishEnteringNewCampaign(it)
                })
            }
        }
        items(campaigns) {
            ListCard(it.name, onClick = {
                onSelectCampaign(it.id)
            })
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            campaignSelectViewModel.cancelNewCampaign()
        }
    }
}