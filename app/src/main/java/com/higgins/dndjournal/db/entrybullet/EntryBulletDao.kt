package com.higgins.dndjournal.db.entrybullet

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryBulletDao {
    @Insert
    suspend fun insertAll(vararg entryBullet: EntryBullet)

    @Query("SELECT * FROM entrybullet WHERE journalEntryId = :journalEntryId")
    fun getBulletsForJournalEntry(journalEntryId: Int): Flow<List<EntryBullet>>

    @Query("UPDATE entryBullet SET content = :content WHERE id = :entryBulletId")
    suspend fun updateContent(entryBulletId: Int, content: String)
}