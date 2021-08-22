package com.higgins.dndnotes

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.higgins.dndnotes.ui.theme.DNDNotesTheme

@Composable
fun Home() {
    DNDNotesTheme {
        Scaffold(topBar = { HomeTopAppBar() }) {
            val navController = rememberNavController()
            AppNavigation(navController = navController)
        }
    }
}

@Composable
fun HomeTopAppBar() {
    TopAppBar(title = { Text("DND Notes") })
}
