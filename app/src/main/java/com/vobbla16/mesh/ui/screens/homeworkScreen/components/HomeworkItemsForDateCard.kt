package com.vobbla16.mesh.ui.screens.homeworkScreen.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vobbla16.mesh.common.toHumanStr
import com.vobbla16.mesh.domain.model.common.LessonSelector
import com.vobbla16.mesh.domain.model.homework.HomeworkItem
import com.vobbla16.mesh.domain.model.homework.HomeworkItemsForDateWithLessonModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

@SuppressLint("NewApi")
@Composable
fun HomeworkItemsForDateCard(
    data: HomeworkItemsForDateWithLessonModel,
    loadingDoneIds: List<Long>,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onClick: (LessonSelector) -> Unit,
    onCheckDone: (HomeworkItem) -> Unit,
) {
    val config = LocalConfiguration.current

    Card(modifier) {
        Text(
            text = data.date.toHumanStr(config),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(6.dp)
        )

        val coroutineScope = rememberCoroutineScope()
        data.items.forEachIndexed { index, item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        item.second?.let {
                            onClick(it)
                        }
                    }
            ) {
                if( loadingDoneIds.contains(item.first.id)) {
                    CircularProgressIndicator(modifier = Modifier.scale(0.6f).size(48.dp))
                } else {
                    Checkbox(checked = item.first.isReady, onCheckedChange = {
                        onCheckDone(item.first)
                    })
                }

                Column(Modifier.padding(0.dp, 2.dp, 6.dp, 4.dp)) {
                    Text(
                        text = item.first.subjectName,
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(text = item.first.description)
                }
            }
            if (index != data.items.size - 1) {
                HorizontalDivider(modifier = Modifier.padding(8.dp, 2.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeworkItemsForDateCardPreview1() {
    HomeworkItemsForDateCard(
        data = HomeworkItemsForDateWithLessonModel(
            date = LocalDate(2022, 10, 7),
            items = listOf(
                Pair(HomeworkItem(
                    id = 1,
                    isReady = true,
                    createdAt = LocalDateTime(2022, 10, 5, 22, 10),
                    updatedAt = LocalDateTime(2022, 10, 5, 22, 11),
                    description = "Простое задание",
                    subjectId = 1337,
                    subjectName = "Физика",
                    teacherId = 1337,
                    dateAssignedOn = LocalDate(2022, 10, 5),
                    datePreparedFor = LocalDate(2022, 10, 7)
                ), null),
                Pair(HomeworkItem(
                    id = 2,
                    isReady = false,
                    createdAt = LocalDateTime(2022, 10, 6, 10, 10),
                    updatedAt = LocalDateTime(2022, 10, 5, 11, 11),
                    description = "Ооооооооочень длинное задание, Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur dictum, diam pulvinar rhoncus malesuada, neque turpis laoreet urna, ac elementum.",
                    subjectId = 1337,
                    subjectName = "Какаято там дичь. предмет с ооооочень длинным названием. предмет с ооооочень длинным названием",
                    teacherId = 1337,
                    dateAssignedOn = LocalDate(2022, 10, 6),
                    datePreparedFor = LocalDate(2022, 10, 7)
                ), null),
                Pair(HomeworkItem(
                    id = 3,
                    isReady = true,
                    createdAt = LocalDateTime(2022, 10, 5, 22, 10),
                    updatedAt = LocalDateTime(2022, 10, 5, 22, 11),
                    description = "Простое задание",
                    subjectId = 1337,
                    subjectName = "Физика",
                    teacherId = 1337,
                    dateAssignedOn = LocalDate(2022, 10, 5),
                    datePreparedFor = LocalDate(2022, 10, 7)
                ), null),
            )
        ),
        modifier = Modifier.padding(32.dp),
        snackbarHostState = SnackbarHostState(),
        loadingDoneIds = listOf(2),
        onCheckDone = {},
        onClick = {}
    )
}