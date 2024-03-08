package com.vobbla16.mesh.ui.screens.marksScreen.components

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vobbla16.mesh.common.Constants
import com.vobbla16.mesh.common.toHumanStr
import com.vobbla16.mesh.domain.model.marks.GradeType
import com.vobbla16.mesh.domain.model.marks.Mark
import com.vobbla16.mesh.domain.model.marks.MarkValue
import com.vobbla16.mesh.ui.screens.marksScreen.localState.MarkWithSubject
import com.vobbla16.mesh.ui.screens.marksScreen.localState.SingleDayReport
import kotlinx.datetime.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SingleDayCard(report: SingleDayReport, onClick: (Long) -> Unit, modifier: Modifier = Modifier) {
    val config = LocalConfiguration.current

    Card(modifier) {
        Text(
            text = report.date.toHumanStr(config),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(6.dp)
        )

        report.marks.forEach { mark ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick(mark.mark.id)
                    },
            ) {
                MarkDefault(
                    mark = mark.mark.toMarkDefaultValue(),
                    modifier = Modifier.padding(6.dp, 4.dp)
                )
                Column(Modifier.padding(8.dp, 2.dp)) {
                    Text(text = mark.subjectName, style = MaterialTheme.typography.labelLarge)
                    Text(
                        text = mark.mark.controlFormName ?: Constants.DEFAULT_STRING,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@SuppressLint("NewApi")
@Preview(showBackground = true)
@Composable
fun SingleDayCardPreview1() {
    val report = SingleDayReport(
        date = LocalDate(2022, 11, 11),
        marks = listOf(
            MarkWithSubject(
                "Математика", Mark(
                    comment = null,
                    controlFormName = "Самостоятельная работа",
                    date = LocalDate(2022, 12, 28),
                    gradeType = GradeType.Five,
                    isPoint = true,
                    pointDate = LocalDate(2022, 12, 28),
                    topic = "Что-то там",
                    value = MarkValue(5f, 100f),
                    weight = 2,
                    id = 0
                )
            ),
            MarkWithSubject(
                "Русский язык", Mark(
                    comment = null,
                    controlFormName = "Самостоятельная работа",
                    date = LocalDate(2022, 12, 28),
                    gradeType = GradeType.Five,
                    isPoint = false,
                    pointDate = LocalDate(2022, 12, 28),
                    topic = "Что-то там",
                    value = MarkValue(5f, 100f),
                    weight = 3,
                    id = 0
                )
            )
        )
    )

    SingleDayCard(report = report, modifier = Modifier.padding(32.dp), onClick = {})
}