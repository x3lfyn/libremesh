package com.vobbla16.mesh.ui.screens.scheduleScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vobbla16.mesh.R
import com.vobbla16.mesh.domain.model.schedule.Activity
import kotlinx.datetime.LocalTime
import kotlin.time.Duration

@Composable
fun ScheduleBreakItem(activity: Activity.Break) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp, 1.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.sprint),
                contentDescription = "icon with running person",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = activity.info,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.labelSmall
            )
        }

        Text(
            text = "${activity.beginTime} - ${activity.endTime}",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.labelSmall
        )

        Text(
            text = "${activity.duration.inWholeMinutes} минут",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleBreakItemPreview1() {
    ScheduleBreakItem(
        activity = Activity.Break(
            "Перемена",
            LocalTime(9, 15),
            LocalTime(9, 25),
            Duration.parse("600s")
        )
    )
}