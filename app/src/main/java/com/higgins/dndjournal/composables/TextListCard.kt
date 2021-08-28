package com.higgins.dndjournal.composables

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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun TextListCard(
    title: String,
    onClick: (() -> Unit)
) {
    BaseListCard(onClick) {
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
@ExperimentalMaterialApi
@Composable
fun EditTextListCard(onDone: (String) -> Unit) {
    val focusRequester = remember { FocusRequester() }
    BaseListCard {
        var campaignName = remember { mutableStateOf(TextFieldValue("")) }
        BasicTextField(
            value = campaignName.value, onValueChange = {
                campaignName.value = it
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
                    onDone(campaignName.value.text)
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

@ExperimentalMaterialApi
@Composable
fun BaseListCard(
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 24.dp, vertical = 8.dp),
        shape = RoundedCornerShape(14.dp),
        elevation = 5.dp,
        onClick = onClick ?: {},
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