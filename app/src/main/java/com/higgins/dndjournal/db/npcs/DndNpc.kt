package com.higgins.dndjournal.db.npcs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.higgins.dndjournal.db.campaign.Campaign

@Entity(
    foreignKeys = [
        ForeignKey(
            onDelete = ForeignKey.CASCADE,
            entity = Campaign::class,
            parentColumns = ["id"],
            childColumns = ["campaignId"],
        )
    ]
)
class DndNpc(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "campaignId") val campaignId: Int,
    @ColumnInfo(name = "name") val name: String
) {
    constructor(campaignId: Int, name: String) : this(0, campaignId, name)
}