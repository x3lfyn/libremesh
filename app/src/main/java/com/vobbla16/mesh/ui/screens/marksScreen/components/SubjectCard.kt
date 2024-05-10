package com.vobbla16.mesh.ui.screens.marksScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vobbla16.mesh.R
import com.vobbla16.mesh.domain.model.marks.GradeType
import com.vobbla16.mesh.domain.model.marks.Mark
import com.vobbla16.mesh.domain.model.marks.MarkValue
import com.vobbla16.mesh.domain.model.marks.MarksSubjectModel
import com.vobbla16.mesh.domain.model.marks.Period
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SubjectCard(
    subject: MarksSubjectModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(modifier = modifier.fillMaxWidth(), onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
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
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
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
            }

            if (subject.periods.isNotEmpty()) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "arrow icon"
                )
            } else {
                Text(
                    text = stringResource(id = R.string.no_marks),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(4.dp, 0.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SubjectCardPreview1() {
    val subj = MarksSubjectModel(
        subjectId = 0,
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
        )
    )
    SubjectCard(subject = subj, onClick = {}, modifier = Modifier.padding(8.dp))
}

@Preview(showBackground = true)
@Composable
fun SubjectCardPreview2() {
    val subj = MarksSubjectModel(
        subjectId = 0,
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
        )
    )
    SubjectCard(subject = subj, onClick = {}, modifier = Modifier.padding(8.dp))
}

@Preview(showBackground = true)
@Composable
fun SubjectCardPreview3() {
    val subj = MarksSubjectModel(
        subjectName = "Основы духовно-нравственной культуры народов России",
        average = MarkValue(fiveScale = 4.51f, hundredScale = 90.0f),
        yearMark = 4,
        examMark = 5,
        attestationMark = 5,
        periods = emptyList(),
        subjectId = 0
    )
    SubjectCard(subject = subj, onClick = {}, modifier = Modifier.padding(8.dp))
}

@Preview(showBackground = true)
@Composable
fun SubjectCardPreview4() {
    val subj = MarksSubjectModel(
        subjectName = "Основы духовно-нравственной культуры народов России 1111111111111111111111111111111111111",
        average = MarkValue(fiveScale = 4.51f, hundredScale = 90.0f),
        yearMark = null,
        examMark = null,
        attestationMark = null,
        subjectId = 0,
        periods = listOf(
            Period(
                name = "I триместр",
                average = MarkValue(fiveScale = 4.53f, hundredScale = 90.53f),
                finalMark = null,
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
        )
    )
    SubjectCard(subject = subj, onClick = {}, modifier = Modifier.padding(8.dp))
}
