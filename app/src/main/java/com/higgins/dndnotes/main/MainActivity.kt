package com.higgins.dndnotes.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.livedata.observeAsState
import com.higgins.dndnotes.Home
import com.higgins.dndnotes.debug.PrepopulateMockDataViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val prepopulateDbViewModel: PrepopulateMockDataViewModel by viewModels()
            val donePopulating = prepopulateDbViewModel.dbDonePopulating.observeAsState(false)
            prepopulateDbViewModel.populateDb()
            if (donePopulating.value) {
                Home()
            }
        }
    }
}

