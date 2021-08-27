package com.higgins.dndjournal.screens.journalentrydetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.util.TableInfo
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
fun JournalEntryDetail(journalEntry: JournalEntry) {
    Column {
        JournalEntryBullet(text = "Here is some information.")
        JournalEntryBullet(
            text = """Here is some very very very very very very very very very very \
            very very very very very very very very very very very very very very very very very\
            very very very very very very very very very long information.""".trimMargin()
        )
    }
}

@Composable
fun JournalEntryBullet(
    text: String,
) {
    Row(
//        modifier = Modifier.height(120.dp)
    ) {
        Box(
//            modifier = Modifier.fillMaxHeight().padding(11.dp)
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier.size(4.dp, 4.dp),
                backgroundColor = Color.Black
            ) {}
        }
        Text(text)
    }
}