package com.higgins.dndnotes.composables

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainList() {
    LazyColumn(modifier = Modifier
        .padding(horizontal = 0.dp, vertical = 12.dp)
        .fillMaxHeight(1f)) {
        item {
            ExpandableListCard("Quests")
        }
        item {
            ExpandableListCard("Locations")
        }
        item {
            ExpandableListCard("NPCs")
        }
        item {
            ExpandableListCard("Tags")
        }
    }
}