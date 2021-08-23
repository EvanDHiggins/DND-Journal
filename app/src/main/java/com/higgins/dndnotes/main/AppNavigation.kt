package com.higgins.dndnotes.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.higgins.dndnotes.composables.CampaignJournal
import com.higgins.dndnotes.composables.CampaignSelect
import java.lang.RuntimeException

open class Route(val route: String) {
    object CampaignSelect : Route("campaign_select")
    object CampaignJournal : Route("campaign/{campaignId}") {

        fun forCampaignId(id: Int): String {
            return "campaign/$id"
        }
    }
}

data class NavigationState(val navController: NavController, val homeViewModel: HomeViewModel)

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun AppNavigation(navController: NavHostController, homeViewModel: HomeViewModel = hiltViewModel()) {
    val navState = NavigationState(navController, homeViewModel)
    NavHost(navController = navController, Route.CampaignSelect.route) {
        addCampaignSelect(navState)
        addCampaignJournal(navState)
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
                "Expected campaignId argument to ${Route.CampaignJournal.route} navigation")
        CampaignJournal(campaignId.toInt())
    }
}