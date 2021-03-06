package com.higgins.dndjournal.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.higgins.dndjournal.composables.appbar.AppBarAction
import com.higgins.dndjournal.state.AppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _displayBackArrow = mutableStateOf(false)
    val displayBackArrow: State<Boolean> = _displayBackArrow

    fun showBackArrow() {
        _displayBackArrow.value = true
    }

    fun hideBackArrow() {
        _displayBackArrow.value = false
    }

    private val _appBarActions = MutableLiveData<List<AppBarAction>>(listOf())
    val appBarActions: LiveData<List<AppBarAction>> = _appBarActions

    fun appBarState(): AppBarState {
        return AppBarState(_appBarActions)
    }

}