package com.higgins.dndnotes.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.higgins.dndnotes.R

@ExperimentalMaterialApi
@Composable
fun ExpandableListCard(
    title: String,
    onClick: (() -> Unit)? = null,
    onAdd: (() -> Unit)? = null,
    onExpand: (() -> Unit)? = null,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 24.dp, vertical = 8.dp),
        shape = RoundedCornerShape(14.dp),
        elevation = 5.dp,
        onClick = onClick ?: {},
        enabled = onClick != null, // Disables click animation if no onClick is provided.
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(1f)
        ) {
            if (onExpand != null) {
                CardArrow(degrees = 0f, onClick = onExpand)
            }
            Text(text = title, modifier = Modifier.padding(16.dp).fillMaxWidth(1f), textAlign = TextAlign.Center)
            if (onAdd != null) {
                NewEntryButton(title = title)
            }
        }
    }
}

@Composable
fun NewEntryButton(title: String) {
    Box(modifier = Modifier.padding(2.dp)) {
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add_symbol),
                contentDescription = "Add new entry for $title."
            )
        }
    }
}

@Composable
fun CardArrow(
    degrees: Float,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(R.drawable.ic_expand_more),
                contentDescription = "Expandable Arrow",
                modifier = Modifier.rotate(degrees),
            )
        }
    )
}