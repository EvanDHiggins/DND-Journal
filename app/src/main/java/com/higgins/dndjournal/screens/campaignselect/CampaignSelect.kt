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
import com.higgins.dndjournal.db.campaign.Campaign
import com.higgins.dndjournal.state.AppBarState

internal enum class CampaignSelectState {
    SELECT, DELETE, ADD;
}

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

    val enteringNewCampaign by campaignSelectViewModel.campaignCreationState.active
        .observeAsState(false)

    val selectedForDeletion by campaignSelectViewModel.selectedForDeletion.observeAsState(setOf())

    val state = when {
        enteringNewCampaign -> CampaignSelectState.ADD
        selectedForDeletion.isNotEmpty() -> CampaignSelectState.DELETE
        else -> CampaignSelectState.SELECT
    }
    println(state)

    ConfigureAppBar(state, appBarState)

    CampaignSelect(
        campaigns,
        state,
        selectedForDeletion,
        onSelectCampaign = onSelectCampaign
    )
}

@Composable
internal fun ConfigureAppBar(
    state: CampaignSelectState,
    appBarState: AppBarState,
    campaignSelectViewModel: CampaignSelectViewModel = hiltViewModel()
) {
    appBarState.setActions(when (state) {
        CampaignSelectState.SELECT, CampaignSelectState.ADD -> AppBarActions.Add {
            campaignSelectViewModel.campaignCreationState.begin()
        }
        CampaignSelectState.DELETE -> AppBarActions.Delete {
            campaignSelectViewModel.deleteSelectedCampaigns()
        }
    })
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
internal fun CampaignSelect(
    campaigns: List<Campaign>,
    state: CampaignSelectState,
    selectedForDeletion: Set<Int>,
    onSelectCampaign: (campaignId: Int) -> Unit,
    campaignSelectViewModel: CampaignSelectViewModel = hiltViewModel()
) {

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 0.dp, vertical = 12.dp)
            .fillMaxHeight(1f)
    ) {
        if (state == CampaignSelectState.ADD) {
            item {
                EditTextListCard(onDone = {
                    campaignSelectViewModel.campaignCreationState.finish(it)
                })
            }
        }
        items(campaigns) { campaign ->
            val clickActions =
                CampaignClickActions(state, onSelectCampaign = {
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
            campaignSelectViewModel.campaignCreationState.cancel()
        }
    }
}

internal class CampaignClickActions(
    state: CampaignSelectState,
    onSelectCampaign: () -> Unit,
    toggleCampaignDeletion: () -> Unit
) {
    val onClick: () -> Unit = if (state == CampaignSelectState.DELETE) {
        toggleCampaignDeletion
    } else {
        onSelectCampaign
    }
    val onLongClick: () -> Unit = toggleCampaignDeletion
}