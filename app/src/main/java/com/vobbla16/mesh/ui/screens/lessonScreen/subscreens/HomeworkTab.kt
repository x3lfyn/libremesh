package com.vobbla16.mesh.ui.screens.lessonScreen.subscreens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.vobbla16.mesh.ui.screens.lessonScreen.LessonScreenViewModel

@Composable
fun HomeworkTab(vm: LessonScreenViewModel) {
    val state = vm.viewState.value

    Text(text = state.lessonInfo.data?.homeworks.toString())
}