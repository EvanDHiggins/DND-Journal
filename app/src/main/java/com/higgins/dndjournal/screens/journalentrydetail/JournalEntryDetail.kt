package com.higgins.dndjournal.screens.journalentrydetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.higgins.dndjournal.R
import com.higgins.dndjournal.composables.NewEntryButton
import com.higgins.dndjournal.db.entrybullet.EntryBullet
import com.higgins.dndjournal.db.journalentry.JournalEntry
import com.higgins.dndjournal.main.Route
import com.higgins.dndjournal.util.applyIf
import com.higgins.dndjournal.util.onBackspace

@ExperimentalComposeUiApi
@Composable
fun JournalEntryDetail(
    journalEntryId: Int,
    journalEntryDetailViewModel: JournalEntryDetailViewModel = hiltViewModel()
) {
    val journalEntry by journalEntryDetailViewModel.loadJournalEntry(journalEntryId)
        .observeAsState()
    if (journalEntry != null) {
        JournalEntryDetail(journalEntry!!)
    }
}

@ExperimentalComposeUiApi
@Composable
fun JournalEntryDetail(
    journalEntry: JournalEntry,
    journalEntryDetailViewModel: JournalEntryDetailViewModel = hiltViewModel()
) {
    val entryBullets by journalEntryDetailViewModel.getBulletsForEntry(journalEntry.id)
        .collectAsState(
            listOf()
        )

    val focusRequester = remember { FocusRequester() }
    val focusedIndex: MutableState<Int?> = remember { mutableStateOf(null) }
    val focusManager = LocalFocusManager.current
    JournalEntryDetailColumn(
        entryBullets,
        updateBullet = { entryId, content ->
            journalEntryDetailViewModel.persistBulletContent(entryId, content)
        },
        onCancel = { entryId ->
            journalEntryDetailViewModel.deleteEntry(entryId)
        },
        newBullet = {
            journalEntryDetailViewModel.newBulletForJournalEntry(journalEntry.id)
        },
    )
}

@ExperimentalComposeUiApi
@Composable
fun JournalEntryDetailColumn(
    entryBullets: List<EntryBullet>,
    updateBullet: (Int, String) -> Unit,
    onCancel: (entryId: Int) -> Unit,
    newBullet: () -> Unit,
) {
    LazyColumn {
        itemsIndexed(entryBullets) { index, entryBullet ->
            JournalEntryBullet(
                text = entryBullet.content,
                onFinishUpdate = { content ->
                    updateBullet(entryBullet.id, content)
                },
                onCancel = {
                    onCancel(entryBullet.id)
                }
            )
        }

        // New Entry Button
        item {
            NewEntryButton(newBullet)
        }
    }
}


@ExperimentalComposeUiApi
@Composable
fun JournalEntryBullet(
    text: String,
    onFinishUpdate: (String) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row {
        val fontSize = 20
        val textStyle = TextStyle(color = Color.Black, fontSize = fontSize.sp)
        Box(
            modifier = Modifier.padding((fontSize / 2).dp)
        ) {
            BulletPoint()
        }
        val textField = remember { mutableStateOf(TextFieldValue(text)) }
        val onBackspace = {
            if (textField.value.text.isEmpty()) {
                onCancel()
                false
            }
            true
        }
        BasicTextField(
            value = textField.value,
            onValueChange = {
                textField.value = it
            },
            textStyle = textStyle,
            modifier = modifier
                .fillMaxWidth(1f)
                .onKeyEvent { keyEvent ->
                    if (keyEvent.type == KeyEventType.KeyUp && keyEvent.key == Key.Backspace) {
                        onBackspace()
                    }
                    true
                },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            // TODO: Make it so that pressing enter defocuses the current bullet, and whenever a
            // new bullet is added it is automatically focused.
            keyboardActions = KeyboardActions(
                onNext = {
                    onFinishUpdate(textField.value.text)
                }
            )
        ) {
            it()
        }
    }
}

@Composable
fun BulletPoint() {
    Card(
        shape = CircleShape,
        modifier = Modifier.size(8.dp, 8.dp),
        backgroundColor = Color.Black
    ) {}
}

@Composable
fun NewEntryButton(newBullet: () -> Unit) {
    IconButton(onClick = newBullet) {
        Icon(
            painter = painterResource(R.drawable.ic_add_symbol),
            "New Bullet",
            modifier = Modifier.alpha(.6f)
        )
    }
}