package com.vobbla16.mesh.ui.screens.subjectScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vobbla16.mesh.R
import com.vobbla16.mesh.common.toShortLocalizedStr
import com.vobbla16.mesh.domain.model.subjectMarks.Mark
import com.vobbla16.mesh.domain.model.subjectMarks.Period
import com.vobbla16.mesh.ui.screens.marksScreen.components.CheckMark
import com.vobbla16.mesh.ui.screens.marksScreen.components.CheckMarkType
import com.vobbla16.mesh.ui.screens.marksScreen.components.MarkDefault
import kotlinx.datetime.toLocalDate

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PeriodTab(period: Period, onMarkClick: (Long) -> Unit, modifier: Modifier = Modifier) {
    val config = LocalConfiguration.current

    Column(modifier) {
        Text(
            text = buildAnnotatedString {
                append(period.startDate.toShortLocalizedStr(config))
                appendInlineContent("arrow")
                append(period.endDate.toShortLocalizedStr(config))
                append(" · ")
                append("${period.marks.size} оценок")
            },
            inlineContent = mapOf("arrow" to InlineTextContent(
                Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.TextCenter)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "arrow icon"
                )
            }),
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.marks_screen_average_mark))
                CheckMark(
                    mark = period.average.toFloat(),
                    bgColor = MaterialTheme.colorScheme.inverseOnSurface,
                    type = CheckMarkType.ZeroCheck,
                    modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp)
                )
            }

            period.fixedMark?.let { final ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(R.string.marks_screen_final_mark))
                    CheckMark(
                        mark = final,
                        bgColor = MaterialTheme.colorScheme.inverseOnSurface,
                        type = CheckMarkType.SingleCheck,
                        modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp)
                    )
                }
            }
        }

        FlowRow {
            period.marks.forEach { mark ->
                MarkDefault(
                    mark = mark.toMarkDefaultValue(),
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { onMarkClick(mark.id) })
            }
        }
    }
}

val sample = Period(
    title = "I полугодие",
    average = "4.67",
    startDate = "2023-09-01".toLocalDate(),
    endDate = "2023-12-31".toLocalDate(),
    fixedMark = 5,
    marks = listOf(
        Mark(
            id = 2077990377,
            value = 5,
            weight = 1,
            comment = "чтение и перевод текста стр 10-11",
            controlForm = "Устный ответ",
            isPoint = false,
            pointDate = null,
            isExam = false
        ),
        Mark(
            id = 2087517701,
            value = 4,
            weight = 1,
            comment = "чтение и перевод текст стр. 15",
            controlForm = "Устный ответ",
            isPoint = false,
            pointDate = null,
            isExam = false
        ),
        Mark(
            id = 2110882822,
            value = 5,
            weight = 1,
            comment = "Чтение и перевод текста стр 30",
            controlForm = "Устный ответ",
            isPoint = false,
            pointDate = null,
            isExam = false
        ),
        Mark(
            id = 2114357469,
            value = 5,
            weight = 1,
            comment = "",
            controlForm = "Устный ответ",
            isPoint = false,
            pointDate = null,
            isExam = false
        ),
        Mark(
            id = 2122724396,
            value = 5,
            weight = 1,
            comment = "Чтение и перевод текста стр 36",
            controlForm = "Устный ответ",
            isPoint = false,
            pointDate = null,
            isExam = false
        ),
        Mark(
            id = 2135338506,
            value = 4,
            weight = 1,
            comment = "Чтение и перевод текста стр 41",
            controlForm = "Устный ответ",
            isPoint = false,
            pointDate = null,
            isExam = false
        ),
        Mark(
            id = 2135422872,
            value = 5,
            weight = 1,
            comment = "Употребление make-do",
            controlForm = "Тест",
            isPoint = false,
            pointDate = null,
            isExam = false
        ),
        Mark(
            id = 2139858622,
            value = 4,
            weight = 1,
            comment = "Самостоятельная работа по 9 временам",
            controlForm = "Тест",
            isPoint = false,
            pointDate = null,
            isExam = false
        ),
        Mark(
            id = 2160445760,
            value = 5,
            weight = 1,
            comment = "Контроль навыка аудирования",
            controlForm = "Аудирование",
            isPoint = false,
            pointDate = null,
            isExam = false
        ),
        Mark(
            id = 2163484985,
            value = 5,
            weight = 1,
            comment = "Чтение и перевод текста  стр 58",
            controlForm = "Устный ответ",
            isPoint = false,
            pointDate = null,
            isExam = false
        ),
        Mark(
            id = 2175956625,
            value = 5,
            weight = 1,
            comment = "Чтение и перевод текста стр 62",
            controlForm = "Устный ответ",
            isPoint = false,
            pointDate = null,
            isExam = false
        ),
        Mark(
            id = 2209317618,
            value = 4,
            weight = 2,
            comment = "Контроль навыка аудирования и чтения (базовый уровень)",
            controlForm = "Контрольная работа",
            isPoint = false,
            pointDate = null,
            isExam = true
        ),
        Mark(
            id = 2211339468,
            value = 5,
            weight = 1,
            comment = "Диктант по словам стр 70 упр 2,5",
            controlForm = "Диктант",
            isPoint = false,
            pointDate = null,
            isExam = false
        ),
        Mark(
            id = 2221734664,
            value = 5,
            weight = 1,
            comment = "Чтение и перевод текста стр 73",
            controlForm = "Устный ответ",
            isPoint = false,
            pointDate = null,
            isExam = false
        )
    )
)

@Preview(showBackground = true)
@Composable
private fun PeriodTabPreview1() {
    Surface {
        PeriodTab(period = sample, onMarkClick = {})
    }
}