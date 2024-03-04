package com.vobbla16.mesh.ui.screens.scheduleScreen.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vobbla16.mesh.R
import com.vobbla16.mesh.domain.model.lessonInfo.AdditionalMaterial
import com.vobbla16.mesh.domain.model.lessonInfo.Homework
import com.vobbla16.mesh.domain.model.lessonInfo.LessonInfoModel
import com.vobbla16.mesh.domain.model.lessonInfo.Mark
import com.vobbla16.mesh.ui.screens.marksScreen.components.MarkDefault
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LessonInfoDisplay(
    lessonInfo: LessonInfoModel, snackbarHostState: SnackbarHostState, modifier: Modifier = Modifier
) {
    Column(modifier.padding(4.dp)) {
        Text(
            text = buildAnnotatedString {
//                append(lessonInfo.beginTime.date.toHumanStr(config))
//                append(", ")
                append(lessonInfo.beginTime.time.toString())
                appendInlineContent("arrow")
                append("${lessonInfo.durationMinutes()} минyт")
                appendInlineContent("arrow")
                append(lessonInfo.endTime.time.toString())
            },
            inlineContent = mapOf("arrow" to InlineTextContent(
                Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.TextCenter)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "arrow icon"
                )
            }),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(text = lessonInfo.subjectName, style = MaterialTheme.typography.headlineSmall)
        Text(
            text = lessonInfo.room.toString(),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(0.dp, 2.dp)
        )
        lessonInfo.teacher?.let {
            Text(text = it, style = MaterialTheme.typography.bodyMedium)
        }

        val scope = rememberCoroutineScope()
        if (lessonInfo.homeworks.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp, 6.dp)
            ) {
                Column(Modifier.padding(4.dp)) {
                    Text(
                        text = "Домашняя работа",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(2.dp)
                    )

                    lessonInfo.homeworks.forEachIndexed { index, homework ->
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = homework.isDone,
                                    onCheckedChange = {
                                        scope.launch { snackbarHostState.showSnackbar("Not yet implemented") }
                                    }
                                )
                                Text(text = homework.name)
                            }
                            homework.additionMaterials.forEach { material ->
                                when (material) {
                                    is AdditionalMaterial.Attachment -> {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier.padding(4.dp, 2.dp)
                                        ) {
                                            Text(
                                                text = material.title,
                                                style = MaterialTheme.typography.labelMedium,
                                                modifier = Modifier.weight(1f)
                                            )
                                            IconButton(onClick = {
                                                scope.launch { snackbarHostState.showSnackbar("Not yet implemented") }
                                            }) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.download),
                                                    contentDescription = "download icon"
                                                )
                                            }
                                        }
                                    }

                                    else -> {}
                                }
                            }
                            if (index != lessonInfo.homeworks.size - 1) {
                                HorizontalDivider(modifier = Modifier.padding(2.dp))
                            }
                        }
                    }
                }
            }
        }

        if (lessonInfo.marks.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp, 6.dp)
            ) {
                Column(Modifier.padding(4.dp)) {
                    Text(text = "Оценки", style = MaterialTheme.typography.titleSmall)

                    lessonInfo.marks.forEach { mark ->
                        Row(
                            modifier = Modifier.padding(2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            MarkDefault(mark = mark.toMarkDefaultValue())
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(2.dp, 0.dp)
                            ) {
                                mark.controlForm?.let {
                                    Text(text = it, style = MaterialTheme.typography.labelMedium)
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
                                                contentDescription = "quotes icon"
                                            )
                                        })
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun LessonInfoDisplayPreview1() {
    LessonInfoDisplay(
        lessonInfo = LessonInfoModel(
            id = 0,
            beginTime = LocalDateTime(2023, 12, 12, 14, 15, 16, 0),
            endTime = LocalDateTime(2023, 12, 12, 15, 15, 16, 0),
            room = "Room #15",
            teacher = "Иванов Иван Иванович",
            subjectId = 0,
            subjectName = "Algebra",
            homeworks = listOf(
                Homework(
                    name = "some homework", isDone = true, additionMaterials = listOf(
                        AdditionalMaterial.Attachment(
                            title = "veryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryveryverylong.pdf",
                            links = listOf("https://example.com")
                        )
                    )
                ),
                Homework(
                    name = "some homework another", isDone = false, additionMaterials = emptyList()
                )
            ),
            marks = listOf(
                Mark(
                    value = 4,
                    weight = 2,
                    comment = null,
                    controlForm = "Самостоятельная работа",
                    isPoint = false
                ), Mark(
                    value = 2,
                    weight = 1,
                    comment = "some comment",
                    controlForm = null,
                    isPoint = true
                ), Mark(
                    value = 3,
                    weight = 3,
                    comment = "some comment",
                    controlForm = "Контрольная работа",
                    isPoint = true
                )
            )
        ), snackbarHostState = SnackbarHostState()
    )
}