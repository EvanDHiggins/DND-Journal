package com.higgins.dndjournal.composables.appbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TextEntryField(modifier: Modifier = Modifier, onDone: (String) -> Unit) {
    var text = remember { mutableStateOf(TextFieldValue("")) }
    val focusRequester = remember { FocusRequester() }
    BasicTextField(
        value = text.value,
        onValueChange = {
            text.value = it
        },
        modifier = modifier
            .fillMaxWidth(1f)
            .focusRequester(focusRequester),
        textStyle = TextStyle(
            textAlign = TextAlign.Center
        ),
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = {
                onDone(text.value.text)
            }
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            autoCorrect = false,
            imeAction = ImeAction.Done
        )
    )
    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose {}
    }
}
