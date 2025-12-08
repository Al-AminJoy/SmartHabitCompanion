package com.alamin.smarthabitcompanion.ui.presentation.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alamin.smarthabitcompanion.core.utils.AppConstants
import com.alamin.smarthabitcompanion.core.utils.Logger
import com.alamin.smarthabitcompanion.ui.presentation.model.WeeklyCompletionUiModel

private const val TAG = "WeeklyCompletionChart"

@Composable
fun WeeklyCompletionChart(modifier: Modifier = Modifier, uiModel: WeeklyCompletionUiModel) {
    LaunchedEffect(uiModel.sevenDayHabits) {
        Logger.log(TAG, "WeeklyCompletionChart: ${uiModel.sevenDayHabits}")
    }

    Column(modifier = modifier) {
        Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN * 2).dp))
        Text(
            "Last 7 Days Completion Chart",
            style = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.Start),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = (AppConstants.APP_MARGIN).dp)
        )
        Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN).dp))
        if (uiModel.sevenDayHabits.isNotEmpty()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppConstants.APP_MARGIN.dp), verticalAlignment = Alignment.Bottom) {
                uiModel.sevenDayHabits.reversed().forEachIndexed { index, pair ->
                    Column (Modifier
                        , horizontalAlignment = Alignment.CenterHorizontally){
                        Text(text = "${pair.second}%", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold))
                        Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN / 2).dp))
                        Spacer(
                            modifier = Modifier .height(pair.second.dp)
                                .width(uiModel.barWidth.dp).padding(horizontal = AppConstants.APP_MARGIN.dp).drawBehind {
                                    this.drawRect(
                                        color = AppConstants.chartColor[index],
                                    )
                                })
                        Spacer(modifier = Modifier.size((AppConstants.APP_MARGIN / 2).dp))
                        Text(text = pair.first, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold))
                    }
                }

            }
        }
    }
}
