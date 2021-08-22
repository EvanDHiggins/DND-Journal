package com.higgins.dndnotes.db.campaign

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Campaign(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String
) {
    constructor(name: String) : this(0, name)
}