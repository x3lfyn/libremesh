package com.vobbla16.mesh.ui.screens.lessonScreen.subscreens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.vobbla16.mesh.ui.screens.lessonScreen.LessonScreenViewModel

@Composable
fun MarksTab(vm: LessonScreenViewModel) {
    val state = vm.viewState.value

    Text(state.lessonInfo.data?.marks.toString())
}