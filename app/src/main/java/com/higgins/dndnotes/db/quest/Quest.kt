package com.higgins.dndnotes.db.quest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.higgins.dndnotes.db.campaign.Campaign

@Entity(
    foreignKeys = [
        ForeignKey(
            onDelete = CASCADE,
            entity = Campaign::class,
            parentColumns = ["id"],
            childColumns = ["campaignId"]
        )
    ]
)
data class Quest(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "campaignId") val campaignId: Int,
    @ColumnInfo(name = "name") val name: String
) {
    constructor(campaignId: Int, name: String) : this(0, campaignId, name)
}