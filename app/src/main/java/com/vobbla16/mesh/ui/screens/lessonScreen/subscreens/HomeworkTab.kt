package com.vobbla16.mesh.ui.screens.lessonScreen.subscreens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vobbla16.mesh.LocalMainVM
import com.vobbla16.mesh.R
import com.vobbla16.mesh.domain.model.lessonInfo.AdditionalMaterial
import com.vobbla16.mesh.domain.model.lessonInfo.Homework
import com.vobbla16.mesh.ui.commonComponents.genericHolderContainer.GenericHolderContainer
import com.vobbla16.mesh.ui.genericHolder.GenericHolder
import com.vobbla16.mesh.ui.screens.lessonScreen.LessonScreenState
import com.vobbla16.mesh.ui.screens.lessonScreen.LessonScreenViewModel
import com.vobbla16.mesh.ui.screens.lessonScreen.Tabs
import kotlinx.coroutines.launch

@Composable
fun HomeworkTab(vm: LessonScreenViewModel) {
    val state = vm.viewState.value

    HomeworkTabUI(
        state = state,
        onRefresh = { vm.refresh() },
        onRetry = { vm.retry() },
        LocalMainVM.current.viewState.value.snackbarHostState
    )
}

@Composable
fun HomeworkTabUI(
    state: LessonScreenState,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    GenericHolderContainer(
        holder = state.lessonInfo,
        onRefresh = onRefresh,
        onRetry = onRetry,
        modifier = Modifier.padding(4.dp)
    ) { model ->
        model.homeworks.forEachIndexed { index, homework ->
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
                                modifier = Modifier.padding(8.dp, 0.dp)
                            ) {
                                Text(
                                    text = material.title,
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(onClick = {
                                    scope.launch {
                                        val browserIntent = Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse(material.links.first())
                                        )
                                        context.startActivity(browserIntent)
                                    }
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
                if (index != model.homeworks.size - 1) {
                    HorizontalDivider(modifier = Modifier.padding(6.dp, 2.dp))
                }
            }
        }
        if (model.homeworks.isEmpty()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.lesson_screen_no_homework),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeworkTabPreview1() {
    val sample = baseSample.copy(
        homeworks = listOf(
            Homework(
                name = "домашнее задание 1",
                isDone = true,
                additionMaterials = emptyList()
            ),
            Homework(
                name = "длинное название длинное название длинное название длинное название длинное название длинное название длинное название длинное название длинное название длинное название длинное название",
                additionMaterials = listOf(
                    AdditionalMaterial.Attachment(
                        title = "приложение 1.pdf",
                        links = listOf("https://ya.ru")
                    )
                ),
                isDone = false
            )
        )
    )

    Surface {
        HomeworkTabUI(state = LessonScreenState(
            lessonInfo = GenericHolder(data = sample),
            selectedLesson = null,
            currentTab = Tabs.Description
        ), onRefresh = {}, onRetry = {}, snackbarHostState = SnackbarHostState()
        )
    }
}