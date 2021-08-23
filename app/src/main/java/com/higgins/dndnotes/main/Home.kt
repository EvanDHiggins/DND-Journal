package com.higgins.dndnotes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.higgins.dndnotes.main.AppNavigation
import com.higgins.dndnotes.ui.theme.DNDNotesTheme

import androidx.lifecycle.asLiveData
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import com.higgins.dndnotes.main.HomeViewModel

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
fun HomeTopAppBar(showBackArrow: Boolean, navController: NavController) {
    TopAppBar(
        title = {
            Text("DnD Journal")
        }, navigationIcon = getNavIconComposable(showBackArrow, navController)
    )
}


fun getNavIconComposable(hasBackStackEntry: Boolean, navController: NavController): (@Composable () -> Unit)? {
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
