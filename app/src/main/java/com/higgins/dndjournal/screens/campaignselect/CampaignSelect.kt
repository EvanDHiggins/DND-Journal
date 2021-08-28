package com.higgins.dndjournal.screens.campaignselect

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
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
import com.higgins.dndjournal.composables.TextListCard
import com.higgins.dndjournal.composables.appbar.AppBarActions
import com.higgins.dndjournal.state.AppBarState


@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun CampaignSelect(
    onSelectCampaign: (campaignId: Int) -> Unit,
    appBarState: AppBarState,
    campaignSelectViewModel: CampaignSelectViewModel = hiltViewModel()
) {

    val campaigns by campaignSelectViewModel.observableCampaigns.observeAsState(listOf())
    val enteringNewCampaign by campaignSelectViewModel.enteringNewCampaign
        .observeAsState(false)
    val selectedForDeletion by campaignSelectViewModel.selectedForDeletion.observeAsState(setOf())

    if (selectedForDeletion.isEmpty()) {

        println("ADDING ADD")
        appBarState.setActions(
            AppBarActions.Add {
                campaignSelectViewModel.beginEnterNewCampaignState()
            }
        )
    } else {
        println("ADDING DELETE")
        appBarState.setActions(
            AppBarActions.Delete {
                campaignSelectViewModel.deleteSelectedCampaigns()
            }
        )
    }
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
        items(campaigns) { campaign ->
            val clickActions =
                CampaignClickActions(selectedForDeletion.isNotEmpty(), onSelectCampaign = {
                    onSelectCampaign(campaign.id)
                }, toggleCampaignDeletion = {
                    campaignSelectViewModel.selectForDeletion(campaign.id)
                })
            TextListCard(
                campaign.name,
                highlighted = selectedForDeletion.contains(campaign.id),
                onClick = clickActions.onClick,
                onLongClick = clickActions.onLongClick
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            campaignSelectViewModel.cancelNewCampaign()
        }
    }
}

class CampaignClickActions(
    deletionOngoing: Boolean,
    onSelectCampaign: () -> Unit,
    toggleCampaignDeletion: () -> Unit
) {
    val onClick: () -> Unit = if (deletionOngoing) {
        toggleCampaignDeletion
    } else {
        onSelectCampaign
    }
    val onLongClick: () -> Unit = toggleCampaignDeletion
}