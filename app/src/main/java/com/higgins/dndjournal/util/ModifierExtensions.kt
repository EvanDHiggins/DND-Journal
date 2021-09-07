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
fun Modifier.interceptKeyPress(key: Key, condition: Boolean, onPress: () -> Unit): Modifier {
    this.onKeyEvent { keyEvent ->
        if (keyEvent.type == KeyEventType.KeyUp && keyEvent.key == key && condition) {
            onPress()
            false
        }
        true
    }

    return this
}

@ExperimentalComposeUiApi
fun Modifier.interceptBackspace(condition: Boolean, onBackspace: () -> Unit): Modifier {
    return this.interceptKeyPress(Key.Backspace, condition, onBackspace)
}

@ExperimentalComposeUiApi
fun Modifier.interceptEnter(onEnter: () -> Unit): Modifier {
    return this.interceptEnter(true, onEnter)
}

@ExperimentalComposeUiApi
fun Modifier.interceptEnter(condition: Boolean, onEnter: () -> Unit): Modifier {
    return this.interceptKeyPress(Key.Enter, condition, onEnter)
}