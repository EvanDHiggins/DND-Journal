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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.higgins.dndjournal.R
import com.higgins.dndjournal.db.journalentry.JournalEntry

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

@Composable
fun JournalEntryDetail(
    journalEntry: JournalEntry,
    journalEntryDetailViewModel: JournalEntryDetailViewModel = hiltViewModel()
) {
    val entryBullets by journalEntryDetailViewModel.getBulletsForEntry(journalEntry.id)
        .collectAsState(
            listOf()
        )

    val focusedIndex = entryBullets.size - 1
    val focusRequester = remember {FocusRequester()}
    LazyColumn {
        itemsIndexed(entryBullets) { index, entryBullet ->
            val modifier = if (index == focusedIndex) {
                Modifier.focusRequester(focusRequester)
            } else {
                Modifier
            }
            JournalEntryBullet(text = entryBullet.content, onUpdate = { content ->
                journalEntryDetailViewModel.persistBulletContent(entryBullet.id, content)
                focusRequester.freeFocus()
            }, modifier = modifier)
        }
        item {
            IconButton(onClick = {
                journalEntryDetailViewModel.newBulletForJournalEntry(journalEntry.id)
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_add_symbol),
                    "New Bullet",
                    modifier = Modifier.alpha(.6f)
                )
            }
        }
    }
}

@Composable
fun JournalEntryBullet(
    text: String,
    onUpdate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row {
        val fontSize = 20
        val textStyle = TextStyle(color = Color.Black, fontSize = fontSize.sp)
        Box(
            modifier = Modifier.padding((fontSize / 2).dp)
        ) {
            Card( // Defines the bullet point for each journal entry.
                shape = CircleShape,
                modifier = Modifier.size(8.dp, 8.dp),
                backgroundColor = Color.Black
            ) {}
        }
        val textField = remember { mutableStateOf(TextFieldValue(text)) }

        BasicTextField(
            value = textField.value,
            onValueChange = {
                textField.value = it
            },
            textStyle = textStyle,
            modifier = modifier.fillMaxWidth(1f),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            // TODO: Make it so that pressing enter defocuses the current bullet, and whenever a
            // new bullet is added it is automatically focused.
            keyboardActions = KeyboardActions(
                onDone = {
                    onUpdate(textField.value.text)
                }
            )
        ) {
            it()
        }
    }
}