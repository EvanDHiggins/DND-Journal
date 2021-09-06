package com.higgins.dndjournal.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import com.higgins.dndjournal.Home
import com.higgins.dndjournal.debug.PrepopulateMockDataViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    @ExperimentalFoundationApi
    @ExperimentalAnimationApi
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

