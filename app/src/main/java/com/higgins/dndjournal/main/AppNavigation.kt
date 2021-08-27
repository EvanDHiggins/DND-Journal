package com.higgins.dndjournal.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.higgins.dndjournal.composables.CampaignJournal
import com.higgins.dndjournal.composables.CampaignSelect
import com.higgins.dndjournal.screens.journalentrydetail.JournalEntryDetail
import java.lang.RuntimeException

open class Route(val route: String) {
    object CampaignSelect : Route("campaign_select")
    object CampaignJournal : Route("campaign/{campaignId}") {
        fun forCampaignId(id: Int): String {
            return "campaign/$id"
        }
    }

    object JournalEntryDetail : Route("journal_entry_detail/{journalEntryId}") {
        fun forJournalEntryId(id: Int): String {
            return "journal_entry_detail/$id"
        }
    }
}

data class NavigationState(val navController: NavController, val homeViewModel: HomeViewModel)

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun AppNavigation(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val navState = NavigationState(navController, homeViewModel)
    NavHost(navController = navController, Route.CampaignSelect.route) {
        addCampaignSelect(navState)
        addCampaignJournal(navState)
        addJournalEntryDetail(navState)
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.addCampaignSelect(navState: NavigationState) {
    composable(Route.CampaignSelect.route) {
        navState.homeViewModel.hideBackArrow()
        CampaignSelect(onSelectCampaign = {
            navState.navController.navigate(Route.CampaignJournal.forCampaignId(it))
        })
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.addCampaignJournal(navState: NavigationState) {
    composable(
        Route.CampaignJournal.route,
        arguments = listOf(navArgument("campaignId") {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        navState.homeViewModel.showBackArrow()
        val campaignId = backStackEntry.arguments?.getString("campaignId")
            ?: throw RuntimeException(
                "Expected campaignId argument to ${Route.CampaignJournal.route} navigation"
            )
        CampaignJournal(campaignId = campaignId.toInt(), openJournalEntry = {
            navState.navController.navigate(Route.JournalEntryDetail.forJournalEntryId(it))
        })
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.addJournalEntryDetail(navState: NavigationState) {
    composable(
        Route.JournalEntryDetail.route,
        arguments = listOf(navArgument("journalEntryId") {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        navState.homeViewModel.showBackArrow()
        val journalEntryId = backStackEntry.arguments?.getString("journalEntryId")
            ?: throw RuntimeException(
                "Expected campaignId argument to ${Route.JournalEntryDetail.route} navigation"
            )

        JournalEntryDetail(journalEntryId.toInt())
    }
}
