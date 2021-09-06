package com.higgins.dndjournal

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.higgins.dndjournal.main.AppNavigation
import com.higgins.dndjournal.ui.theme.DNDNotesTheme

import com.higgins.dndjournal.main.HomeViewModel

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun Home(viewModel: HomeViewModel = hiltViewModel()) {
    DNDNotesTheme {
        val navController = rememberNavController()
        val showBackArrow by viewModel.displayBackArrow
        Scaffold(topBar = { HomeTopAppBar(showBackArrow, navController) }) {
            AppNavigation(navController = navController)
        }
    }
}

@Composable
fun HomeTopAppBar(
    showBackArrow: Boolean,
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    TopAppBar(
        title = {
            Text("DnD Journal")
        },
        navigationIcon = getNavIconComposable(showBackArrow, navController),
        actions = {
            val actions by homeViewModel.appBarActions.observeAsState(listOf())
            for (action in actions) {
                action.Icon()
            }
        }
    )
}

fun getNavIconComposable(
    hasBackStackEntry: Boolean,
    navController: NavController
): (@Composable () -> Unit)? {
    if (!hasBackStackEntry) {
        return null
    }
    return {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_arrow_back_24),
                contentDescription = "Navigate Up"
            )
        }
    }
}
