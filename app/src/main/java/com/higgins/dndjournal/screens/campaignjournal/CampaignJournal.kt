package com.higgins.dndjournal.composables

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.higgins.dndjournal.db.journalentry.JournalEntry
import com.higgins.dndjournal.db.journaltype.Journal
import com.higgins.dndjournal.screens.campaignjournal.CampaignJournalViewModel


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun CampaignJournal(
    campaignId: Int,
    openJournalEntry: (journalEntryId: Int) -> Unit,
    campaignJournalViewModel: CampaignJournalViewModel = hiltViewModel()
) {
    val journals by campaignJournalViewModel.observableJournals(campaignId).collectAsState(listOf())
    val expandedJournalIds by campaignJournalViewModel.expandedJournalIds.observeAsState(listOf())
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 0.dp, vertical = 12.dp)
            .fillMaxHeight(1f)
    ) {
        items(journals) {
            val entries by campaignJournalViewModel.entriesForJournal(it.id)
                .collectAsState(listOf())
            ExpandableJournal(
                it,
                entries = entries,
                state = if (it.id in expandedJournalIds) {
                    JournalCategoryState.EXPANDED
                } else {
                    JournalCategoryState.COLLAPSED
                },
                openJournalEntry = openJournalEntry,
            )

        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun ExpandableJournal(
    journal: Journal,
    entries: List<JournalEntry>,
    state: JournalCategoryState,
    openJournalEntry: (journalEntryId: Int) -> Unit,
    campaignJournalViewModel: CampaignJournalViewModel = hiltViewModel()
) {
    ExpandableJournalCategory(
        journal.name,
        onAdd = {},
        state = state,
        onExpandPressed = {
            campaignJournalViewModel.toggleCategorySelection(journal.id)
        },
    ) {
        Column(modifier = Modifier.wrapContentSize(Alignment.Center)) {
            for (indexedEntry in entries.withIndex()) {
                JournalEntryRow(
                    title = indexedEntry.value.title,
                    shouldDrawBottomBorder = indexedEntry.index < entries.size - 1,
                    onClick = { openJournalEntry(indexedEntry.value.id) },
                )
            }
        }
    }
}

@Composable
fun JournalEntryRow(
    title: String,
    shouldDrawBottomBorder: Boolean,
    onClick: () -> Unit
) {
    Box(modifier = Modifier.height(45.dp)) {
        TextButton(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background,
            ),
            shape = RectangleShape,
            modifier = Modifier
                .fillMaxWidth(1f)
                .align(Alignment.Center)
        ) {
            Text(title)
        }
        if (shouldDrawBottomBorder) {
            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}