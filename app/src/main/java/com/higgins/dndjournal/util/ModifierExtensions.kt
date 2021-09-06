package com.higgins.dndjournal.util

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*


fun Modifier.applyIf(condition: Boolean, func: (Modifier) -> Unit): Modifier {
    if (condition) {
        func(this)
    }
    return this
}

@ExperimentalComposeUiApi
fun Modifier.onBackspace(onBackspace: () -> Boolean): Modifier {
    this

    return this
}