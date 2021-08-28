package com.higgins.dndjournal.composables.appbar

import androidx.annotation.DrawableRes
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.higgins.dndjournal.R

/** Base class to describe an AppBar action. */
open abstract class AppBarAction(
    private val onClick: () -> Unit,
    private val hoverText: String,
    @DrawableRes private val drawableId: Int
) {
    @Composable
    fun Icon() {
        IconButton(onClick = onClick) {
            Icon(painter = painterResource(drawableId), hoverText)
        }
    }
}

/**
 * Collection of available actions for the AppBar.
 *
 * Screens are expected to pass these into AppBarState to configure the TopAppBar.
 */
class AppBarActions private constructor() {
    class Accept(onClick: () -> Unit) :
        AppBarAction(onClick, "Accept", R.drawable.ic_baseline_check_24)

    class Add(onClick: () -> Unit) :
        AppBarAction(onClick, "New", R.drawable.ic_add_symbol)

    class Delete(onClick: () -> Unit) :
        AppBarAction(onClick, "Delete", R.drawable.ic_baseline_delete_24)
}