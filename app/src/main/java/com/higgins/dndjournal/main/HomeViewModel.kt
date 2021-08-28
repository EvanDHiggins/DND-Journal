package com.higgins.dndjournal.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class AppBarActions(private val appBarAddAction: MutableLiveData<(() -> Unit?)>) {
    fun removeAddAction() {
        appBarAddAction.value = null
    }

    fun setAppBarAddAction(action: () -> Unit) {
        appBarAddAction.value = action
    }
}

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

    private val _appBarAddAction = MutableLiveData<(() -> Unit)?>(null)


}