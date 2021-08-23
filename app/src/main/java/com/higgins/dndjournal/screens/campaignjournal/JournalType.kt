package com.higgins.dndjournal.screens.campaignjournal

enum class JournalType {
    QUESTS, LOCATIONS, NPCS, TAGS;

    override fun toString(): String {
        return when(this) {
            QUESTS -> "Quests"
            LOCATIONS -> "Locations"
            NPCS -> "NPCs"
            TAGS -> "Tags"
        }
    }
}