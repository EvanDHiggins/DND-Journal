package com.higgins.dndjournal.db.journalentry

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalEntryDao {
    @Insert
    suspend fun insertAll(vararg location: JournalEntry)

    @Query("SELECT * FROM journalentry WHERE journalId = :journalId")
    fun getEntriesForJournal(journalId: Int): Flow<List<JournalEntry>>
}