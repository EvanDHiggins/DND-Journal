package com.higgins.dndjournal.state

import androidx.lifecycle.MutableLiveData

class AppBarState(private val appBarAddAction: MutableLiveData<(() -> Unit)?>) {
    fun clearActions() {
        appBarAddAction.value = null
    }

    fun setAppBarAddAction(action: () -> Unit) {
        appBarAddAction.value = action
    }
}