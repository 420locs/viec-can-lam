package com.ninhtbm.vcl.utils

import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role
import kotlinx.datetime.Clock

fun Modifier.throttleClick(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    throttleTime: Long = 700L, onClick: () -> Unit
): Modifier = composed {

    var lastClickTime by remember { mutableLongStateOf(0L) }

    Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
    ) {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        if (currentTime - lastClickTime > throttleTime) {
            lastClickTime = currentTime
            onClick()
        }
    }
}
