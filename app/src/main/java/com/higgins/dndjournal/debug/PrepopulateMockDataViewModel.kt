package com.higgins.dndjournal.debug

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.higgins.dndjournal.db.DndJournalDatabase
import com.higgins.dndjournal.db.campaign.Campaign
import com.higgins.dndjournal.db.journalentry.JournalEntry
import com.higgins.dndjournal.db.journaltype.Journal
import com.higgins.dndjournal.db.tags.DndTag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrepopulateMockDataViewModel @Inject constructor(val db: DndJournalDatabase) : ViewModel() {
    private val _dbDonePopulating = MutableLiveData<Boolean>(false)
    val dbDonePopulating: LiveData<Boolean> = _dbDonePopulating

    fun populateDb() {
        viewModelScope.launch {
            _populateDb()
            _dbDonePopulating.value = true
        }
    }

    private fun List<Journal>.getJournalByName(name: String): Journal {
        for (journal in this) {
            if (journal.name == name) {
                return journal
            }
        }
        throw RuntimeException(
            "[PREPOPULATION ERROR]: Failed to find journal with name $name." +
                    "\n\nOnly found these: $this."
        )
    }

    private suspend fun _populateDb() {
        db.campaignDao().deleteAll()
        db.campaignDao().insertAll(Campaign("Matt's Campaign"))
        db.campaignDao().insertAll(Campaign("Other Campaign"))
        db.campaignDao().insertAll(Campaign("Secret Campaign"))

        val campaign = db.campaignDao().getByName("Matt's Campaign")

        db.journalDao().insertAll(
            Journal(campaign.id, "Quests"),
            Journal(campaign.id, "Locations"),
            Journal(campaign.id, "NPCs")
        );

        val journals = db.journalDao().getJournalsForCampaignAsync(campaign.id)
        val questJournal = journals.getJournalByName("Quests")
        val locationJournal = journals.getJournalByName("Locations")
        val npcJournal = journals.getJournalByName("NPCs")

        db.journalEntryDao().insertAll(
            JournalEntry(questJournal.id, "Find the lost person", "They were last seen a mile away."),
            JournalEntry(questJournal.id, "Kill some Draugr", "There is a cave nearby."),
            JournalEntry(questJournal.id, "Run a mile", "This seems really hard...")
        )

        db.journalEntryDao().insertAll(
            JournalEntry(locationJournal.id, "Mordor", "Spooky..."),
            JournalEntry(locationJournal.id,  "Gondor", "Cool")
        )

        db.journalEntryDao().insertAll(
           JournalEntry(npcJournal.id, "Dave", "nice guy"),
            JournalEntry(npcJournal.id, "Rudiger", "is a bird"),
        )

        db.tagDao().insertAll(
            DndTag(campaign.id, "Treasure"),
            DndTag(campaign.id, "Weapon")
        )
    }
}