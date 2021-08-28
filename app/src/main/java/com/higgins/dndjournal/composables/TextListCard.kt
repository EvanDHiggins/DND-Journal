package com.higgins.dndjournal.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun TextListCard(
    title: String,
    highlighted: Boolean = false,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
) {
    BaseListCard(highlighted, onClick, onLongClick) {
        Text(
            text = title, modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(1f), textAlign = TextAlign.Center
        )
    }
}

/**
 * Creates a list card with an autofocused text entry field.
 *
 * Calls {@param onDone} with the entered value on completion.
 */
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun EditTextListCard(onDone: (String) -> Unit) {
    val focusRequester = remember { FocusRequester() }
    BaseListCard {
        var text = remember { mutableStateOf(TextFieldValue("")) }
        BasicTextField(
            value = text.value, onValueChange = {
                text.value = it
            },
            modifier = Modifier
                .padding(16.dp)
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
            ), keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                imeAction = ImeAction.Done
            )
        )
    }
    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose {}
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun BaseListCard(
    highlighted: Boolean = false,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .combinedClickable(
                enabled = true,
                onLongClick = onLongClick,
                onClick = onClick ?: {},
            ),
        backgroundColor = MaterialTheme.colors.background,
        border = if (highlighted) BorderStroke(2.dp, Color.Black) else null,
        shape = RoundedCornerShape(14.dp),
        elevation = 5.dp,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(1f)
        ) {
            content()
        }
    }
}