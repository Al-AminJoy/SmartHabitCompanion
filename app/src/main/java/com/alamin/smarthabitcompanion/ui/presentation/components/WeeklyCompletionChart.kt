package com.alamin.smarthabitcompanion.ui.presentation.components

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.ui.presentation.model.WeeklyCompletionUiModel

private const val TAG = "WeeklyCompletionChart"

@Composable
fun WeeklyCompletionChart(modifier: Modifier = Modifier, uiModel: WeeklyCompletionUiModel) {
    var showAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(uiModel.sevenDayHabits) {
        if (uiModel.sevenDayHabits.isNotEmpty()) {
            showAnimation = true
        }
    }

    val barAnimation =
        remember(uiModel.sevenDayHabits) { List(uiModel.sevenDayHabits.size) { Animatable(0F) } }


    Column(modifier = modifier) {
        Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN * 2).dp))

        Text(
            "Last 7 Days Completion Chart",
            style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = (AppConstants.APP_MARGIN).dp)
        )

        Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN * 3).dp))

        if (showAnimation && uiModel.sevenDayHabits.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppConstants.APP_MARGIN.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                uiModel.sevenDayHabits.sortedBy { it.first }.forEachIndexed { index, pair ->
                    Column(
                        Modifier, horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        LaunchedEffect(showAnimation) {
                            barAnimation[index].animateTo(
                                pair.second.toFloat(),
                                animationSpec = tween(
                                    durationMillis = 800,
                                    easing = LinearEasing
                                )
                            )
                        }

                        Box(
                            modifier = Modifier
                                .width(uiModel.barWidth.dp)
                                .height(100.dp),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Canvas(
                                Modifier
                                    .height(barAnimation[index].value.dp)
                                    .width(uiModel.barWidth.dp)
                                    .padding(horizontal = AppConstants.APP_MARGIN.dp)
                            ) {
                                drawRect(color = AppConstants.chartColor[index])
                                drawContext.canvas.nativeCanvas.apply {
                                    val paint = Paint().apply {
                                        color = Color.GRAY
                                        textSize = 8.dp.toPx()
                                        textAlign = Paint.Align.CENTER
                                        isFakeBoldText = true
                                    }
                                    drawText("${pair.second}%", size.width / 2, -10f, paint)
                                }

                            }

                        }

                        Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN / 2).dp))

                        Text(
                            text = "${pair.first.split("-")[2]}-${pair.first.split("-")[1]}",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }

                }
            }
        }
    }
}
