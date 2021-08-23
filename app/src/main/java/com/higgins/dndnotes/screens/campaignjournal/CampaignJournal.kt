package com.higgins.dndnotes.composables

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.higgins.dndnotes.screens.campaignjournal.CampaignJournalViewModel

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun CampaignJournal(
    campaignId: Int,
    campaignJournalViewModel: CampaignJournalViewModel = hiltViewModel()
) {
    val expandedCategories by campaignJournalViewModel.expandedCategories.observeAsState(setOf())
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 0.dp, vertical = 12.dp)
            .fillMaxHeight(1f)
    ) {
        item {
            //QuestsList(campaignId)
            val questEntries by campaignJournalViewModel.observableQuests(campaignId)
                .collectAsState(listOf())

            CategoryList(
                "Quests",
                campaignId,
                expandedCategories,
                questEntries.map { it.name },
                onExpandPressed = {
                    campaignJournalViewModel.toggleCategorySelection(campaignId)
                })
        }
        item {
            ExpandableJournalCategory("Locations", onAdd = {})
        }
        item {
            ExpandableJournalCategory("NPCs", onAdd = {})
        }
        item {
            ExpandableJournalCategory("Tags", onAdd = {})
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun CategoryList(
    title: String,
    campaignId: Int,
    expandedCategories: Set<Int>,
    entries: List<String>,
    onExpandPressed: () -> Unit,
) {
    ExpandableJournalCategory(
        title,
        onAdd = {},
        state = getJournalCategoryStateForCategory(campaignId, expandedCategories),
        onExpandPressed = onExpandPressed,
    ) {
        Column {
            for (entry in entries) {
                // TODO: Add real list entry instead of a garbage placeholder.
                Text(entry)
            }
        }
    }
}

fun getJournalCategoryStateForCategory(
    categoryId: Int,
    expandedCategories: Set<Int>
): JournalCategoryState {
    return if (expandedCategories.contains(categoryId)) {
        JournalCategoryState.EXPANDED
    } else {
        JournalCategoryState.COLLAPSED
    }
}