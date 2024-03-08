package com.vobbla16.mesh.ui.screens.marksScreen.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import com.vobbla16.mesh.common.toHumanStr
import com.vobbla16.mesh.domain.model.marks.GradeType
import com.vobbla16.mesh.domain.model.marks.Mark
import com.vobbla16.mesh.domain.model.marks.MarkValue
import com.vobbla16.mesh.domain.model.marks.Period
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalLayoutApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PeriodCard(period: Period, onClick: (Long) -> Unit, modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current

    Card(modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Text(text = period.name, style = MaterialTheme.typography.titleSmall)

            Text(
                text =
                "${period.startDate.toHumanStr(configuration)} - " +
                        period.endDate.toHumanStr(configuration)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Среднее:", modifier = Modifier.padding(4.dp))
                CheckMark(
                    mark = period.average.fiveScale,
                    bgColor = MaterialTheme.colorScheme.inverseOnSurface,
                    type = CheckMarkType.ZeroCheck
                )
            }

            period.finalMark?.let { final ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Финальная:", modifier = Modifier.padding(4.dp))
                    CheckMark(
                        mark = final,
                        bgColor = MaterialTheme.colorScheme.inverseOnSurface,
                        type = CheckMarkType.SingleCheck
                    )
                }
            }
        }

        FlowRow {
            period.marks.forEach { mark ->
                MarkDefault(
                    mark = mark.toMarkDefaultValue(),
                    modifier = Modifier
                        .padding(6.dp)
                        .clickable {
                            onClick(mark.id)
                        })
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PeriodCardPreview1() {
    val period = Period(
        name = "I триместр",
        average = MarkValue(fiveScale = 4.53f, hundredScale = 90.53f),
        finalMark = 4,
        startDate = LocalDate(2022, 9, 1),
        endDate = LocalDate(2022, 11, 30),
        isYearMark = false,
        marks = listOf(
            Mark(
                comment = null,
                controlFormName = "Практическая работа",
                date = LocalDate(2022, 9, 1),
                gradeType = GradeType.Five,
                isPoint = false,
                pointDate = null,
                topic = "Механика.Неравномерное движение",
                value = MarkValue(fiveScale = 4.0f, hundredScale = 80.0f),
                weight = 2,
                id = 0
            ), Mark(
                comment = null,
                controlFormName = "Комбинированная работа",
                date = LocalDate(2022, 9, 1),
                gradeType = GradeType.Five,
                isPoint = false,
                pointDate = null,
                topic = "Механика.Неравномерное движение",
                value = MarkValue(fiveScale = 5.0f, hundredScale = 100.0f),
                weight = 2,
                id = 0
            ), Mark(
                comment = null,
                controlFormName = "Решение задач",
                date = LocalDate(2022, 9, 1),
                gradeType = GradeType.Five,
                isPoint = false,
                pointDate = null,
                topic = "Механика.Неравномерное движение",
                value = MarkValue(fiveScale = 4.0f, hundredScale = 80.0f),
                weight = 2,
                id = 0
            ), Mark(
                comment = null,
                controlFormName = "Решение задач",
                date = LocalDate(2022, 9, 1),
                gradeType = GradeType.Five,
                isPoint = false,
                pointDate = null,
                topic = "Механика.Неравномерное движение",
                value = MarkValue(fiveScale = 4.0f, hundredScale = 80.0f),
                weight = 2,
                id = 0
            ), Mark(
                comment = "Там какой то коммент к оценке",
                controlFormName = "Решение задач",
                date = LocalDate(2022, 9, 1),
                gradeType = GradeType.Five,
                isPoint = false,
                pointDate = null,
                topic = "Механика.Неравномерное движение",
                value = MarkValue(fiveScale = 5.0f, hundredScale = 100.0f),
                weight = 2,
                id = 0
            )
        )
    )

    PeriodCard(period = period, onClick = {})
}