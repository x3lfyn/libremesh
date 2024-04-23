package com.vobbla16.mesh.ui.screens.lessonScreen.subscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vobbla16.mesh.R
import com.vobbla16.mesh.common.structures.toHumanStr
import com.vobbla16.mesh.domain.model.lessonInfo.LessonInfoModel
import com.vobbla16.mesh.domain.model.lessonInfo.Mark
import com.vobbla16.mesh.ui.commonComponents.genericHolderContainer.GenericHolderContainer
import com.vobbla16.mesh.ui.genericHolder.GenericHolder
import com.vobbla16.mesh.ui.screens.lessonScreen.LessonScreenState
import com.vobbla16.mesh.ui.screens.lessonScreen.LessonScreenViewModel
import com.vobbla16.mesh.ui.screens.lessonScreen.Tabs
import com.vobbla16.mesh.ui.screens.marksScreen.components.MarkDefault
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

@Composable
fun MarksTab(vm: LessonScreenViewModel) {
    val state = vm.viewState.value

    MarksTabUI(state = state, onRetry = { vm.retry() }, onRefresh = { vm.refresh() })
}

@Composable
fun MarksTabUI(state: LessonScreenState, onRetry: () -> Unit, onRefresh: () -> Unit) {
    GenericHolderContainer(
        holder = state.lessonInfo,
        onRefresh = onRefresh,
        onRetry = onRetry,
        modifier = Modifier.padding(4.dp)
    ) { model ->
        model.marks.forEach { mark ->
            Card(modifier = Modifier.padding(1.dp, 2.dp)) {
                Column(Modifier.padding(5.dp)) {
                    Row(
                        modifier = Modifier.padding(2.dp, 0.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MarkDefault(
                            mark = mark.toMarkDefaultValue(),
                            modifier = Modifier.padding(2.dp)
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp, 0.dp)
                        ) {
                            mark.controlForm?.let {
                                Text(text = it, style = MaterialTheme.typography.labelLarge)
                            }
                            if (mark.isExam) {
                                Text(
                                    text = stringResource(R.string.lesson_screen_mark_exam),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                            mark.pointDate?.let { pointDate ->
                                Text(
                                    text = "${stringResource(R.string.lesson_screen_point_until)} ${pointDate.dayOfMonth}.${pointDate.monthNumber}.${pointDate.year}",
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    }
                    mark.comment?.let {
                        Text(
                            text = buildAnnotatedString {
                                appendInlineContent("quotes")
                                append(it)
                            }, inlineContent = mapOf("quotes" to InlineTextContent(
                                Placeholder(
                                    20.sp,
                                    18.sp,
                                    PlaceholderVerticalAlign.AboveBaseline
                                )
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.format_quote),
                                    contentDescription = "quotes icon",
                                    modifier = Modifier.scale(0.9f)
                                )
                            }),
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(4.dp, 4.dp)
                        )
                    }
                    Text(
                        text = "${stringResource(R.string.lesson_screen_mark_created_at)} ${mark.createdAt.toHumanStr()}",
                        style = MaterialTheme.typography.labelMedium
                    )
                    if (mark.createdAt != mark.updatedAt) {
                        Text(
                            text = "${stringResource(R.string.lesson_screen_mark_updated_at)} ${mark.updatedAt.toHumanStr()}",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
        if (model.marks.isEmpty()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(id = R.string.no_marks),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

val baseSample = LessonInfoModel(
    id = 0,
    subjectId = 0,
    subjectName = "Основы духовно-нравственной культуры народов россии",
    room = "Корпус 1, кабинет математики, 38",
    isMissed = false,
    homeworks = emptyList(),
    teacher = "Петров Петр Петрович",
    marks = emptyList(),
    beginTime = LocalDateTime(2022, 12, 12, 12, 12, 12),
    endTime = LocalDateTime(2022, 12, 12, 12, 57, 12)
)

@Preview(showBackground = true)
@Composable
private fun MarksTabPreview1() {
    Surface {
        MarksTabUI(state = LessonScreenState(
            lessonInfo = GenericHolder(
                data = baseSample.copy(
                    marks = listOf(
                        Mark(
                            id = 0,
                            value = 5,
                            comment = null,
                            weight = 1,
                            isPoint = false,
                            controlForm = "Самостоятельная работа",
                            pointDate = null,
                            isExam = true,
                            createdAt = LocalDateTime(2022, 12, 12, 12, 12, 12),
                            updatedAt = LocalDateTime(2022, 12, 12, 13, 13, 12)
                        ),
                        Mark(
                            id = 0,
                            value = 4,
                            comment = "комментарий к оценке от учителя",
                            weight = 2,
                            isPoint = true,
                            controlForm = "Контрольная работа",
                            pointDate = LocalDate(2022, 12, 12),
                            isExam = false,
                            createdAt = LocalDateTime(2022, 12, 12, 12, 12, 12),
                            updatedAt = LocalDateTime(2022, 12, 12, 12, 12, 12)
                        ),
                        Mark(
                            id = 0,
                            value = 5,
                            comment = "meow",
                            weight = 1,
                            isPoint = true,
                            controlForm = "Самостоятельная работа",
                            pointDate = LocalDate(2022, 12, 12),
                            isExam = true,
                            createdAt = LocalDateTime(2022, 12, 12, 12, 12, 12),
                            updatedAt = LocalDateTime(2022, 12, 12, 13, 13, 12)
                        ),
                    )
                )
            ),
            selectedLesson = null,
            currentTab = Tabs.Description
        ), onRetry = {}, onRefresh = {})
    }
}