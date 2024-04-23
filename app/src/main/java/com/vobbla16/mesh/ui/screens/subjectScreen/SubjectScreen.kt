package com.vobbla16.mesh.ui.screens.subjectScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ramcosta.composedestinations.annotation.Destination
import com.vobbla16.mesh.LocalMainVM
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination
fun SubjectScreen(
    subjectId: Long
) {
    val mainVM = LocalMainVM.current
    val vm: SubjectScreenViewModel = koinViewModel()
    val state = vm.viewState.value

    LaunchedEffect(key1 = Unit) {
        mainVM.showBottomBar()
        vm.selectSubject(subjectId)
    }

    Text(text = state.toString())
}