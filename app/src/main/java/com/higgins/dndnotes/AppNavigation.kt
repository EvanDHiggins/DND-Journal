package com.higgins.dndnotes

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.higgins.dndnotes.composables.MainList

open class Route(val route: String) {
    object Main : Route("main")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, Route.Main.route) {
        addMainScreen(navController = navController)
    }
}

fun NavGraphBuilder.addMainScreen(navController: NavController) {
    composable(Route.Main.route) {
        MainList()
    }
}