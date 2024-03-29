package com.vobbla16.mesh.ui.screens.lessonScreen.subscreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.HorizontalDivider
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
import com.vobbla16.mesh.domain.model.lessonInfo.LessonInfoModel
import com.vobbla16.mesh.domain.model.lessonInfo.Mark
import com.vobbla16.mesh.ui.commonComponents.genericHolderContainer.GenericHolderContainer
import com.vobbla16.mesh.ui.genericHolder.GenericHolder
import com.vobbla16.mesh.ui.screens.lessonScreen.LessonScreenState
import com.vobbla16.mesh.ui.screens.lessonScreen.LessonScreenViewModel
import com.vobbla16.mesh.ui.screens.lessonScreen.Tabs
import com.vobbla16.mesh.ui.screens.marksScreen.components.MarkDefault
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
        model.marks.forEachIndexed { index, mark ->
            Column(modifier = Modifier.padding(2.dp)) {
                Row(
                    modifier = Modifier.padding(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MarkDefault(mark = mark.toMarkDefaultValue(), modifier = Modifier.padding(2.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp, 0.dp)
                    ) {
                        mark.controlForm?.let {
                            Text(text = it, style = MaterialTheme.typography.labelMedium)
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
                        modifier = Modifier.padding(4.dp, 0.dp)
                    )
                }
            }
            if (index != model.marks.size - 1) {
                HorizontalDivider(modifier = Modifier.padding(6.dp, 4.dp))
            }
        }
        if (model.marks.isEmpty()) {
            Text(text = stringResource(id = R.string.no_marks), style = MaterialTheme.typography.titleLarge)
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
            lessonInfo = GenericHolder(data = baseSample.copy(marks = listOf(
                Mark(
                    value = 5,
                    comment = null,
                    weight = 1,
                    isPoint = false,
                    controlForm = "Самостоятельная работа"
                ),
                Mark(
                    value = 4,
                    comment = "комментарий к оценке от учителя",
                    weight = 2,
                    isPoint = true,
                    controlForm = "Контрольная работа"
                )
            ))),
            selectedLesson = null,
            currentTab = Tabs.Description
        ), onRetry = {}, onRefresh = {})
    }
}