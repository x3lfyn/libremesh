package com.vobbla16.mesh.ui.screens.scheduleScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vobbla16.mesh.common.firstDayOfWeek
import com.vobbla16.mesh.common.weekFromFirstDay
import kotlinx.datetime.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayOfWeekPicker(
    selectedDay: LocalDate,
    modifier: Modifier = Modifier,
    onChange: (LocalDate) -> Unit
) {
    val range = remember(selectedDay) {
        weekFromFirstDay(firstDayOfWeek(selectedDay))
    }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
    ) {
        for (day in range) {
            Card(
                colors = CardDefaults.cardColors(containerColor = if (day == selectedDay) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent),
                modifier = Modifier.padding(0.dp, 2.dp),
                onClick = { onChange(day) }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(6.dp, 1.dp)

                ) {
                    Text(
                        text = day.dayOfMonth.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = day.dayOfWeek.getDisplayName(
                            TextStyle.SHORT,
                            Locale.getDefault()
                        ),
                        style = MaterialTheme.typography.labelMedium
                    )
                }

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DayOfWeekPickerPreview1() {
    Box(Modifier.fillMaxWidth()) {
        DayOfWeekPicker(
            selectedDay = LocalDate(2023, 12, 4),
            onChange = {})
    }
}