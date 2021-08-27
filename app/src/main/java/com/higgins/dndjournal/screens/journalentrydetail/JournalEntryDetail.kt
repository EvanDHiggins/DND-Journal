package com.higgins.dndjournal.screens.journalentrydetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.util.TableInfo
import com.higgins.dndjournal.db.journalentry.JournalEntry
import java.lang.RuntimeException

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
    LazyColumn {
        items(entryBullets) {
            JournalEntryBullet(text = it.content)
        }
    }
}

@Composable
fun JournalEntryBullet(
    text: String,
) {
    Row {
        val fontSize = 16
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
        var textField = remember { mutableStateOf(TextFieldValue(text)) }

        BasicTextField(
            value = textField.value,
            onValueChange = {
                textField.value = it
            },
            textStyle = textStyle,
        ) {
            it()
        }
    }
}