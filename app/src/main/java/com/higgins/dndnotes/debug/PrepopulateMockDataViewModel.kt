package com.higgins.dndnotes.debug

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.higgins.dndnotes.db.DndJournalDatabase
import com.higgins.dndnotes.db.campaign.Campaign
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

    private suspend fun _populateDb() {
        db.campaignDao().deleteAll()
        db.campaignDao().insertAll(Campaign("Matt's Campaign"))
        db.campaignDao().insertAll(Campaign("Other Campaign"))
        db.campaignDao().insertAll(Campaign("Secret Campaign"))

        db.campaignDao().getByName("Matt's Campaign")
    }
}