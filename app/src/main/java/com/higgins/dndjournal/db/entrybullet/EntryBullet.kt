package com.higgins.dndjournal.db.entrybullet

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.higgins.dndjournal.db.journalentry.JournalEntry

@Entity(
    foreignKeys = [
        ForeignKey(
            onDelete = ForeignKey.CASCADE,
            entity = JournalEntry::class,
            parentColumns = ["id"],
            childColumns = ["journalEntryId"],
        )
    ]
)
data class EntryBullet(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "journalEntryId") val journalEntryId: Int,
    @ColumnInfo(name = "content") val content: String
) {
    constructor(journalEntryId: Int, content: String) : this(0, journalEntryId, content)
}