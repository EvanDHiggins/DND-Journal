package com.higgins.dndjournal.screens.campaignjournal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.higgins.dndjournal.composables.appbar.TextEntryField

@Composable
fun EditableJournalEntryRow(
    shouldDrawBottomBorder: Boolean,
    onDone: (String) -> Unit,
) {
    BasicJournalEntryRow(
        shouldDrawBottomBorder = shouldDrawBottomBorder
    ) {
        TextEntryField(modifier = Modifier.align(Alignment.Center), onDone = onDone)
    }
}

@Composable
fun JournalEntryRow(
    title: String,
    shouldDrawBottomBorder: Boolean,
    onClick: () -> Unit,
) {
    BasicJournalEntryRow(
        shouldDrawBottomBorder = shouldDrawBottomBorder
    ) {
        TextButton(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background,
            ),
            shape = RectangleShape,
            modifier = Modifier
                .fillMaxWidth(1f)
                .align(Alignment.Center)
        ) {
            Text(title)
        }
    }
}

@Composable
fun BasicJournalEntryRow(
    shouldDrawBottomBorder: Boolean,
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = Modifier.height(45.dp)) {
        content()
        if (shouldDrawBottomBorder) {
            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}