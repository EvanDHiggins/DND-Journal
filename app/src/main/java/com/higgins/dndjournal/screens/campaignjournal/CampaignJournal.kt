package com.higgins.dndjournal.composables

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.higgins.dndjournal.screens.campaignjournal.CampaignJournalViewModel
import com.higgins.dndjournal.screens.campaignjournal.JournalType


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun CampaignJournal(
    campaignId: Int,
    campaignJournalViewModel: CampaignJournalViewModel = hiltViewModel()
) {
    val expandedJournals by campaignJournalViewModel.expandedJournals.observeAsState(setOf())
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 0.dp, vertical = 12.dp)
            .fillMaxHeight(1f)
    ) {
        item {
            val questEntries by campaignJournalViewModel.observableQuests(campaignId)
                .collectAsState(listOf())
            CategoryList(
                journalType = JournalType.QUESTS,
                expandedJournals = expandedJournals,
                entries = questEntries.map { it.name }
            )
        }
        item {
            val locationEntries by campaignJournalViewModel.observableLocations(campaignId)
                .collectAsState(listOf())
            CategoryList(
                journalType = JournalType.LOCATIONS,
                expandedJournals = expandedJournals,
                entries = locationEntries.map { it.name }
            )
        }
        item {
            val npcEntries by campaignJournalViewModel.observableNpcs(campaignId)
                .collectAsState(listOf())
            CategoryList(
                journalType = JournalType.NPCS,
                expandedJournals = expandedJournals,
                entries = npcEntries.map { it.name }
            )
        }
        item {
            val tagEntries by campaignJournalViewModel.observableTags(campaignId).collectAsState(
                listOf()
            )
            CategoryList(
                journalType = JournalType.TAGS,
                expandedJournals = expandedJournals,
                entries = tagEntries.map { it.name }
            )
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun CategoryList(
    journalType: JournalType,
    expandedJournals: Set<JournalType>,
    entries: List<String>,
    campaignJournalViewModel: CampaignJournalViewModel = hiltViewModel()
) {
    CategoryList(
        journalType.toString(),
        journalType,
        expandedJournals,
        entries,
        onExpandPressed = {
            campaignJournalViewModel.toggleCategorySelection(journalType)
        })
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun CategoryList(
    title: String,
    journalType: JournalType,
    expandedJournals: Set<JournalType>,
    entries: List<String>,
    onExpandPressed: () -> Unit,
) {
    ExpandableJournalCategory(
        title,
        onAdd = {},
        state = getJournalCategoryStateForCategory(journalType, expandedJournals),
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
    journalType: JournalType,
    expandedCategories: Set<JournalType>
): JournalCategoryState {
    return if (expandedCategories.contains(journalType)) {
        JournalCategoryState.EXPANDED
    } else {
        JournalCategoryState.COLLAPSED
    }
}