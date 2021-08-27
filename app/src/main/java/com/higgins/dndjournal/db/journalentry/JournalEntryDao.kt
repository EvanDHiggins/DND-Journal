package com.higgins.dndjournal.db.journalentry

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalEntryDao {

    @Query("SELECT * FROM journalentry WHERE id = :entryId")
    fun getJournalEntry(entryId: Int): LiveData<JournalEntry>

    @Insert
    suspend fun insertAll(vararg location: JournalEntry)

    @Query("SELECT * FROM journalentry WHERE journalId = :journalId")
    fun getEntriesForJournal(journalId: Int): Flow<List<JournalEntry>>
}