package com.higgins.dndjournal.composables

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.higgins.dndjournal.R

enum class JournalCategoryState {
    COLLAPSED, EXPANDED;

    fun isExpanded() = this == EXPANDED
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ExpandableJournalCategory(
    title: String,
    onAdd: (() -> Unit),
    state: JournalCategoryState = JournalCategoryState.COLLAPSED,
    onExpandPressed: () -> Unit = {},
    content: @Composable (AnimatedVisibilityScope.() -> Unit)? = null,
) {
    val currentState by remember { mutableStateOf(state) }
    val transition = updateTransition(currentState)

    val arrowRotationDegrees by transition.animateFloat(label = "Flip Arrow") {
        if (state.isExpanded()) 180f else 0f
    }

    JournalCard(
        title = title,
        state = state,
        arrowRotationDegrees = arrowRotationDegrees,
        onExpandPressed = onExpandPressed,
        onAdd = onAdd
    ) {
        content?.invoke(this)
    }
}

@ExperimentalAnimationApi
@Composable
fun JournalCard(
    title: String,
    state: JournalCategoryState,
    arrowRotationDegrees: Float,
    onExpandPressed: () -> Unit,
    onAdd: () -> Unit,
    content: @Composable() (AnimatedVisibilityScope.() -> Unit)?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 24.dp, vertical = 8.dp),
        shape = RoundedCornerShape(14.dp),
        elevation = 5.dp
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                CardArrow(degrees = arrowRotationDegrees, onClick = onExpandPressed)
                Text(title, Modifier.padding(16.dp), textAlign = TextAlign.Center)
                Box {
                    NewEntryButton(title, onAdd)
                }

            }
            ExpandableContent(state.isExpanded()) {
                content?.invoke(this)
            }
        }
    }
}

const val EXPAND_ANIMATION_DURATION = 200

@ExperimentalAnimationApi
@Composable
fun ExpandableContent(visible: Boolean, content: @Composable AnimatedVisibilityScope.() -> Unit) {
    val enterExpand = remember {
        expandVertically(animationSpec = tween(EXPAND_ANIMATION_DURATION))
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(EXPAND_ANIMATION_DURATION))
    }
    AnimatedVisibility(visible, enter = enterExpand, exit = exitCollapse) {
        this.content()
    }
}

@Composable
fun NewEntryButton(title: String, onClick: () -> Unit) {
    Box(modifier = Modifier.padding(2.dp)) {
        IconButton(onClick = onClick) {
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