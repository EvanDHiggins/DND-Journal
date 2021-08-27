package com.higgins.dndjournal.db.journalentry

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.higgins.dndjournal.db.journaltype.Journal

@Entity(
    foreignKeys = [
        ForeignKey(
            onDelete = ForeignKey.CASCADE,
            entity = Journal::class,
            parentColumns = ["id"],
            childColumns = ["journalId"],
        ),
    ]
)
class JournalEntry(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "journalId") val journalId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String
) {
    constructor(journalId: Int, title: String) : this(0, journalId, title, "")
    constructor(journalId: Int, title: String, content: String) : this(
        0,
        journalId,
        title,
        content
    )
}