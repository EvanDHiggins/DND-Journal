package com.higgins.dndjournal.screens.campaignjournal

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.higgins.dndjournal.composables.EditTextListCard
import com.higgins.dndjournal.composables.ExpandableJournalCategory
import com.higgins.dndjournal.composables.JournalCategoryState
import com.higgins.dndjournal.composables.appbar.AppBarAction
import com.higgins.dndjournal.composables.appbar.AppBarActions
import com.higgins.dndjournal.composables.appbar.TextEntryField
import com.higgins.dndjournal.db.journalentry.JournalEntry
import com.higgins.dndjournal.db.journaltype.Journal
import com.higgins.dndjournal.screens.campaignjournal.CampaignJournalViewModel
import com.higgins.dndjournal.state.AppBarState


@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun CampaignJournal(
    campaignId: Int,
    appBarState: AppBarState,
    openJournalEntry: (journalEntryId: Int) -> Unit,
    campaignJournalViewModel: CampaignJournalViewModel = hiltViewModel()
) {
    val journals by campaignJournalViewModel.observableJournals(campaignId).collectAsState(listOf())
    val expandedJournalIds by campaignJournalViewModel.expandedJournalIds.observeAsState(listOf())
    val creatingNewJournal by campaignJournalViewModel.journalCreation.active.observeAsState(false)

    appBarState.setActions(
        AppBarActions.Add {
            campaignJournalViewModel.journalCreation.begin(campaignId)
        }
    )

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 0.dp, vertical = 12.dp)
            .fillMaxHeight(1f)
    ) {
        if (creatingNewJournal) {
            item {
                EditTextListCard(onDone = {
                    campaignJournalViewModel.journalCreation.finish(it)
                })
            }
        }
        items(journals) { journal ->
            val entries by campaignJournalViewModel.entriesForJournal(journal.id)
                .collectAsState(listOf())
            ExpandableJournal(
                journal,
                entries = entries,
                state = if (journal.id in expandedJournalIds) {
                    JournalCategoryState.EXPANDED
                } else {
                    JournalCategoryState.COLLAPSED
                },
                openJournalEntry = openJournalEntry,
            )

        }
    }

    DisposableEffect(Unit) {
        onDispose {
            campaignJournalViewModel.journalCreation.cancel()
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
    val creatingNewJournalEntry by campaignJournalViewModel.journalEntryCreation.active
        .observeAsState(false)
    ExpandableJournalCategory(
        journal.name,
        onAdd = {
            campaignJournalViewModel.collapseAllExcept(journal.id)
            campaignJournalViewModel.journalEntryCreation.begin(journal.id)
        },
        state = state,
        onExpandPressed = {
            campaignJournalViewModel.toggleCategorySelection(journal.id)
        },
    ) {
        Column(modifier = Modifier.wrapContentSize(Alignment.Center)) {
            if (creatingNewJournalEntry) {
                EditableJournalEntryRow(true) {
                    campaignJournalViewModel.journalEntryCreation.finish(it)
                }
            }
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
fun EditableJournalEntryRow(
    shouldDrawBottomBorder: Boolean,
    onDone: (String) -> Unit,
) {
    BasicJournalEntryRow(
        shouldDrawBottomBorder = shouldDrawBottomBorder
    ) {
        TextEntryField(modifier = Modifier.align(Alignment.Center), onDone = onDone)
    }
}

@Composable
fun JournalEntryRow(
    title: String,
    shouldDrawBottomBorder: Boolean,
    onClick: () -> Unit,
) {
    BasicJournalEntryRow(
        shouldDrawBottomBorder = shouldDrawBottomBorder
    ) {
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
    }
}


@Composable
fun BasicJournalEntryRow(
    shouldDrawBottomBorder: Boolean,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = Modifier.height(45.dp)) {
        content()
        if (shouldDrawBottomBorder) {
            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}