package com.higgins.dndjournal.screens.journalentrydetail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.higgins.dndjournal.db.journalentry.JournalEntry

@Composable
fun JournalEntryDetail(
    journalEntryId: Int,
    journalEntryDetailViewModel: JournalEntryDetailViewModel = hiltViewModel()
) {
    val journalEntry by journalEntryDetailViewModel.loadJournalEntry(journalEntryId).observeAsState()
    if (journalEntry != null) {
        Text(text = journalEntry!!.content)
    }
}