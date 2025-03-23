package com.ninhtbm.vcl.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun CircularCountdownIndicator(
    progress: Animatable<Float, AnimationVector1D>,
    totalTime: Long,
    isPause: Boolean,
    modifier: Modifier = Modifier,
    color: Color = Color.Blue,
    strokeWidth: Dp = 20.dp,
) {


    Canvas(
        modifier = modifier
            .padding(strokeWidth / 4)
            .size(200.dp)
    ) {
        val sweepAngle = -360 * progress.value
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(width = strokeWidth.value, cap = StrokeCap.Round),
            size = Size(size.width, size.height)
        )
    }

    // Start or resume the countdown
    LaunchedEffect(isPause) {
        if (!isPause) {
            progress.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = totalTime.toInt(),
                    easing = LinearEasing
                )
            )
        } else {
            progress.stop()
        }
    }
}

data class CircularCountdownIndicatorData(
    val isPause: Boolean,
    val totalTime: Long,
    val progress: Animatable<Float, AnimationVector1D>,
    val resetTimer: suspend () -> Unit,
    val pauseTimer: () -> Unit,
    val playTimer: () -> Unit,
)

@Composable
fun rememberCircularCountdownIndicatorController(
    totalTime: Long
): CircularCountdownIndicatorData {
    var isPause by remember { mutableStateOf(false) }
    var totalTimer by remember { mutableLongStateOf(totalTime) }
    val progress = remember { Animatable(1f) }

    val pauseTimer = remember { { isPause = true } }
    val playTimer = remember { { isPause = false } }
    val resetTimer = remember(progress) {
        suspend {
            pauseTimer()
            progress.stop()
            progress.snapTo(1f)
        }
    }

    return remember(
        isPause,
        totalTimer,
        progress,
        resetTimer,
        pauseTimer,
        playTimer,
    ) {
        CircularCountdownIndicatorData(
            isPause = isPause,
            totalTime = totalTimer,
            progress = progress,
            resetTimer = resetTimer,
            pauseTimer = pauseTimer,
            playTimer = playTimer,
        )
    }
}

@Preview
@Composable
fun CircularCountdownIndicatorPreview() {
    fun convertToSecond(milliseconds: Float): String {
        val totalSeconds = milliseconds.toInt() / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return when {
            hours + minutes == 0 -> String.format(
                locale = Locale.getDefault(),
                format = "%02d",
                seconds
            )
            hours == 0 -> String.format(
                locale = Locale.getDefault(),
                format = "%02d:%02d",
                minutes,
                seconds
            )

            else -> String.format(
                locale = Locale.getDefault(),
                format = "%02d:%02d:%02d",
                hours,
                minutes,
                seconds
            )
        }
    }

    val countdownTimerController = rememberCircularCountdownIndicatorController(
        61_000L
    )
    val coroutineScope = rememberCoroutineScope()
    val size = 200
    Column {
        Box(
            contentAlignment = Alignment.Center
        ) {

            CircularCountdownIndicator(
                progress = countdownTimerController.progress,
                isPause = countdownTimerController.isPause,
                totalTime = countdownTimerController.totalTime,
                color = Color.Red,
                strokeWidth = (size/20).dp,
                modifier = Modifier
                    .size(size.dp)
            )
            Text(
                fontSize = (size/10).sp,
                color = Color.Red,
                text = convertToSecond(countdownTimerController.totalTime * countdownTimerController.progress.value))
        }
        Button(
            onClick = if (countdownTimerController.isPause) {
                countdownTimerController.playTimer
            } else {
                countdownTimerController.pauseTimer
            }
        ) {
            Text(
                if (countdownTimerController.isPause) "Play" else "Pause"
            )
        }
        Button(
            onClick = {
                coroutineScope.launch {
                    countdownTimerController.resetTimer()
                }
            }
        ) {
            Text("Reset")
        }
    }
}