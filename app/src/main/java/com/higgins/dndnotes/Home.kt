package com.higgins.dndnotes

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.higgins.dndnotes.ui.theme.DNDNotesTheme

@Composable
fun Home() {
    DNDNotesTheme {
        Scaffold(topBar = { HomeTopAppBar() }) {

        }
    }
}

@Composable
fun HomeTopAppBar() {
    TopAppBar(title = { Text("DND Notes") })
}
