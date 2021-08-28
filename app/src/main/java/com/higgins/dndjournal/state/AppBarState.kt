package com.higgins.dndjournal.state

import androidx.lifecycle.MutableLiveData
import com.higgins.dndjournal.composables.appbar.AppBarAction

class AppBarState(
    private val appBarActions: MutableLiveData<List<AppBarAction>>,
) {
    fun clearActions() {
        appBarActions.value = listOf()
    }

    fun setActions(vararg actions: AppBarAction) {
        appBarActions.value = actions.toList()
    }

}