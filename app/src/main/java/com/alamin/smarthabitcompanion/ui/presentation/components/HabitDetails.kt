package com.alamin.smarthabitcompanion.ui.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attractions
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.ui.presentation.model.HabitDetailsUiModel
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@Composable
fun HabitDetails(modifier: Modifier = Modifier,uiModel: HabitDetailsUiModel) {

    val config = LocalConfiguration.current
    val height = config.screenHeightDp

    var startAnimation by remember { mutableStateOf(false) }
    var initialAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(uiModel.habitRecord) {
        if (uiModel.habitRecord.isNotEmpty()) {
            startAnimation = true
        }
    }

    LaunchedEffect(initialAnimation) {
        delay(500)
        initialAnimation = true
    }


    val completeProgress by
    animateFloatAsState(
        if (startAnimation) uiModel.completePercent else 0f,
        tween(1000, easing = LinearEasing),

        )

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = { completeProgress.absoluteValue / 100 },
                modifier = Modifier
                    .size((height / 6).dp),
                color = MaterialTheme.colorScheme.secondary,
                strokeWidth = 8.dp,
                trackColor = MaterialTheme.colorScheme.secondary.copy(alpha = .60f),
                strokeCap = StrokeCap.Round,
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(imageVector = Icons.Default.Attractions, contentDescription = null)
                Spacer(modifier = Modifier.height(((AppConstants.APP_MARGIN/2)).dp))
                Text(
                    "${uiModel.progress}/${uiModel.habit.target ?: 1}",
                    style = MaterialTheme.typography.titleLarge
                )
                if (!uiModel.habit.targetUnit.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height((AppConstants.APP_MARGIN/2).dp))
                    Text(uiModel.habit.targetUnit, style = MaterialTheme.typography.titleMedium)
                }
                Spacer(modifier = Modifier.height((AppConstants.APP_MARGIN/2).dp))
                Text("Today", style = MaterialTheme.typography.titleMedium)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                imageVector = Icons.Default.LocalFireDepartment,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary
            )

            Text(
                "${uiModel.habit.streakCount} days streak",
                style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.End),
                modifier = Modifier.padding(AppConstants.APP_MARGIN.dp)
            )
        }
        Spacer(modifier = Modifier.height(AppConstants.APP_MARGIN.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(
                    RoundedCornerShape(
                        topStart = (AppConstants.APP_MARGIN * 2).dp,
                        topEnd = (AppConstants.APP_MARGIN * 2).dp
                    )
                )
                .background(MaterialTheme.colorScheme.onPrimary)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.padding(AppConstants.APP_MARGIN.dp))
                Text(
                    "Weekly Performance",
                    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground),
                    modifier = Modifier.padding(horizontal = AppConstants.APP_MARGIN.dp)
                )
                Spacer(modifier = Modifier.padding(AppConstants.APP_MARGIN.dp))


                if (uiModel.habitRecord.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = AppConstants.APP_MARGIN.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {

                        val barAnimation =
                            remember(uiModel.habitGroup) { List(uiModel.habitGroup.size) { Animatable(0F) } }

                        uiModel.habitGroup.entries.sortedBy { it.key }.forEachIndexed { index, group ->
                            val totalProgress = group.value.sumOf { it.progress }
                            val progressPercent =
                                (totalProgress.toFloat() / (uiModel.habit.target ?: 1).toFloat()) * 100
                            Column(
                                Modifier, horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN / 2).dp))

                                LaunchedEffect(startAnimation) {
                                    barAnimation[index].animateTo(
                                        progressPercent,
                                        animationSpec = tween(
                                            durationMillis = 800,
                                            easing = LinearEasing
                                        )
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .height(100.dp)
                                        .width(uiModel.barWidth.dp)
                                        .padding(horizontal = AppConstants.APP_MARGIN.dp),
                                    contentAlignment = Alignment.BottomCenter
                                ) {
                                    Canvas(
                                        modifier = Modifier
                                            .height(barAnimation[index].value.dp)
                                            .width(uiModel.barWidth.dp)
                                    ) {
                                        drawRect(
                                            color = AppConstants.chartColor[index],
                                        )
                                        drawContext.canvas.nativeCanvas.apply {
                                            val paint = android.graphics.Paint().apply {
                                                color = android.graphics.Color.GRAY
                                                textSize = 12.dp.toPx()
                                                textAlign = android.graphics.Paint.Align.CENTER
                                                isFakeBoldText = true
                                            }
                                            drawText(
                                                "${progressPercent.toInt()}%",
                                                size.width / 2,
                                                -10f,
                                                paint
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN / 2).dp))
                                Text(
                                    text = "${group.key.split("-")[2]}-${group.key.split("-")[1]}",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                )
                            }
                        }

                    }
                }
            }

        }

    }

}