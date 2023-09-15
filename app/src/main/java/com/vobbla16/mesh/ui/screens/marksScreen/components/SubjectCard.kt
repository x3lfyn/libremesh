package com.vobbla16.mesh.ui.screens.marksScreen.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vobbla16.mesh.domain.model.marks.GradeType
import com.vobbla16.mesh.domain.model.marks.Mark
import com.vobbla16.mesh.domain.model.marks.MarkValue
import com.vobbla16.mesh.domain.model.marks.MarksSubjectModel
import com.vobbla16.mesh.domain.model.marks.Period
import kotlinx.datetime.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SubjectCard(
    subject: MarksSubjectModel, opened: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    OutlinedCard(modifier = modifier.fillMaxWidth(), onClick = onClick) {
        FlowRow(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Text(
                text = subject.subjectName, style = MaterialTheme.typography.titleMedium
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                subject.periods.filter {
                    it.finalMark != null
                }.forEach { subj ->
                    CheckMark(
                        mark = subj.finalMark!!,
                        bgColor = MaterialTheme.colorScheme.secondaryContainer,
                        type = CheckMarkType.SingleCheck,
                        modifier = Modifier.padding(4.dp)
                    )
                }

                subject.yearMark?.let { yearMark ->
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "arrow forward icon"
                    )
                    CheckMark(
                        mark = yearMark,
                        bgColor = MaterialTheme.colorScheme.tertiaryContainer,
                        type = CheckMarkType.DoubleCheck,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

            if (subject.periods.isNotEmpty()) {
                val rotationDegree: Float by animateFloatAsState(
                    if (opened) 90f else 0f, label = "is opened indicator"
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "arrow icon",
                    modifier = Modifier.rotate(rotationDegree)
                )
            }
        }

        AnimatedVisibility(visible = opened) {
            Column {
                subject.periods.reversed().forEach { period ->
                    PeriodCard(period = period, modifier = Modifier.padding(4.dp))
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun SubjectCardPreview1() {
    val subj = MarksSubjectModel(
        subjectName = "Физика",
        average = MarkValue(fiveScale = 4.51f, hundredScale = 90.0f),
        yearMark = 4,
        examMark = 5,
        attestationMark = 5,
        periods = listOf(
            Period(
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
                        weight = 2
                    ), Mark(
                        comment = null,
                        controlFormName = "Комбинированная работа",
                        date = LocalDate(2022, 9, 1),
                        gradeType = GradeType.Five,
                        isPoint = false,
                        pointDate = null,
                        topic = "Механика.Неравномерное движение",
                        value = MarkValue(fiveScale = 5.0f, hundredScale = 100.0f),
                        weight = 2
                    ), Mark(
                        comment = null,
                        controlFormName = "Решение задач",
                        date = LocalDate(2022, 9, 1),
                        gradeType = GradeType.Five,
                        isPoint = false,
                        pointDate = null,
                        topic = "Механика.Неравномерное движение",
                        value = MarkValue(fiveScale = 4.0f, hundredScale = 80.0f),
                        weight = 2
                    ), Mark(
                        comment = null,
                        controlFormName = "Решение задач",
                        date = LocalDate(2022, 9, 1),
                        gradeType = GradeType.Five,
                        isPoint = false,
                        pointDate = null,
                        topic = "Механика.Неравномерное движение",
                        value = MarkValue(fiveScale = 4.0f, hundredScale = 80.0f),
                        weight = 2
                    ), Mark(
                        comment = "Там какой то коммент к оценке",
                        controlFormName = "Решение задач",
                        date = LocalDate(2022, 9, 1),
                        gradeType = GradeType.Five,
                        isPoint = false,
                        pointDate = null,
                        topic = "Механика.Неравномерное движение",
                        value = MarkValue(fiveScale = 5.0f, hundredScale = 100.0f),
                        weight = 2
                    )
                )
            ), Period(
                name = "II триместр",
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
                        weight = 2
                    ), Mark(
                        comment = null,
                        controlFormName = "Комбинированная работа",
                        date = LocalDate(2022, 9, 1),
                        gradeType = GradeType.Five,
                        isPoint = false,
                        pointDate = null,
                        topic = "Механика.Неравномерное движение",
                        value = MarkValue(fiveScale = 5.0f, hundredScale = 100.0f),
                        weight = 2
                    ), Mark(
                        comment = null,
                        controlFormName = "Решение задач",
                        date = LocalDate(2022, 9, 1),
                        gradeType = GradeType.Five,
                        isPoint = false,
                        pointDate = null,
                        topic = "Механика.Неравномерное движение",
                        value = MarkValue(fiveScale = 4.0f, hundredScale = 80.0f),
                        weight = 2
                    ), Mark(
                        comment = null,
                        controlFormName = "Решение задач",
                        date = LocalDate(2022, 9, 1),
                        gradeType = GradeType.Five,
                        isPoint = false,
                        pointDate = null,
                        topic = "Механика.Неравномерное движение",
                        value = MarkValue(fiveScale = 4.0f, hundredScale = 80.0f),
                        weight = 2
                    ), Mark(
                        comment = "Там какой то коммент к оценке",
                        controlFormName = "Решение задач",
                        date = LocalDate(2022, 9, 1),
                        gradeType = GradeType.Five,
                        isPoint = false,
                        pointDate = null,
                        topic = "Механика.Неравномерное движение",
                        value = MarkValue(fiveScale = 5.0f, hundredScale = 100.0f),
                        weight = 2
                    )
                )
            )
        )
    )
    SubjectCard(subject = subj, opened = false, onClick = {}, modifier = Modifier.padding(8.dp))
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun SubjectCardPreview2() {
    val subj = MarksSubjectModel(
        subjectName = "Основы духовно-нравственной культуры народов России",
        average = MarkValue(fiveScale = 4.51f, hundredScale = 90.0f),
        yearMark = 4,
        examMark = 5,
        attestationMark = 5,
        periods = listOf(
            Period(
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
                        weight = 2
                    ), Mark(
                        comment = null,
                        controlFormName = "Комбинированная работа",
                        date = LocalDate(2022, 9, 1),
                        gradeType = GradeType.Five,
                        isPoint = false,
                        pointDate = null,
                        topic = "Механика.Неравномерное движение",
                        value = MarkValue(fiveScale = 5.0f, hundredScale = 100.0f),
                        weight = 2
                    ), Mark(
                        comment = null,
                        controlFormName = "Решение задач",
                        date = LocalDate(2022, 9, 1),
                        gradeType = GradeType.Five,
                        isPoint = false,
                        pointDate = null,
                        topic = "Механика.Неравномерное движение",
                        value = MarkValue(fiveScale = 4.0f, hundredScale = 80.0f),
                        weight = 2
                    ), Mark(
                        comment = null,
                        controlFormName = "Решение задач",
                        date = LocalDate(2022, 9, 1),
                        gradeType = GradeType.Five,
                        isPoint = false,
                        pointDate = null,
                        topic = "Механика.Неравномерное движение",
                        value = MarkValue(fiveScale = 4.0f, hundredScale = 80.0f),
                        weight = 2
                    ), Mark(
                        comment = "Там какой то коммент к оценке",
                        controlFormName = "Решение задач",
                        date = LocalDate(2022, 9, 1),
                        gradeType = GradeType.Five,
                        isPoint = false,
                        pointDate = null,
                        topic = "Механика.Неравномерное движение",
                        value = MarkValue(fiveScale = 5.0f, hundredScale = 100.0f),
                        weight = 2
                    )
                )
            ), Period(
                name = "II триместр",
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
                        weight = 2
                    ), Mark(
                        comment = null,
                        controlFormName = "Комбинированная работа",
                        date = LocalDate(2022, 9, 1),
                        gradeType = GradeType.Five,
                        isPoint = false,
                        pointDate = null,
                        topic = "Механика.Неравномерное движение",
                        value = MarkValue(fiveScale = 5.0f, hundredScale = 100.0f),
                        weight = 2
                    ), Mark(
                        comment = null,
                        controlFormName = "Решение задач",
                        date = LocalDate(2022, 9, 1),
                        gradeType = GradeType.Five,
                        isPoint = false,
                        pointDate = null,
                        topic = "Механика.Неравномерное движение",
                        value = MarkValue(fiveScale = 4.0f, hundredScale = 80.0f),
                        weight = 2
                    ), Mark(
                        comment = null,
                        controlFormName = "Решение задач",
                        date = LocalDate(2022, 9, 1),
                        gradeType = GradeType.Five,
                        isPoint = false,
                        pointDate = null,
                        topic = "Механика.Неравномерное движение",
                        value = MarkValue(fiveScale = 4.0f, hundredScale = 80.0f),
                        weight = 2
                    ), Mark(
                        comment = "Там какой то коммент к оценке",
                        controlFormName = "Решение задач",
                        date = LocalDate(2022, 9, 1),
                        gradeType = GradeType.Five,
                        isPoint = false,
                        pointDate = null,
                        topic = "Механика.Неравномерное движение",
                        value = MarkValue(fiveScale = 5.0f, hundredScale = 100.0f),
                        weight = 2
                    )
                )
            )
        )
    )
    SubjectCard(subject = subj, opened = true, onClick = {}, modifier = Modifier.padding(8.dp))
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun SubjectCardPreview3() {
    val subj = MarksSubjectModel(
        subjectName = "Основы духовно-нравственной культуры народов России",
        average = MarkValue(fiveScale = 4.51f, hundredScale = 90.0f),
        yearMark = 4,
        examMark = 5,
        attestationMark = 5,
        periods = emptyList()
    )
    SubjectCard(subject = subj, opened = true, onClick = {}, modifier = Modifier.padding(8.dp))
}
