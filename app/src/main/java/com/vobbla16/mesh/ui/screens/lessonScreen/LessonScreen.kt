package com.vobbla16.mesh.ui.screens.lessonScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vobbla16.mesh.LocalMainVM
import com.vobbla16.mesh.domain.model.common.LessonSelector
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun LessonScreen(
    navigator: DestinationsNavigator,
    lessonSelector: LessonSelector
) {
    val mainVM = LocalMainVM.current
    val vm: LessonScreenViewModel = koinViewModel()
    val state = vm.viewState.value

    LaunchedEffect(key1 = Unit) {
        mainVM.showBottomBar()
        vm.setSelectedLesson(lessonSelector)
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    state.lessonInfo.data?.subjectName?.let { subjName ->
                        Text(text = subjName)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navigator.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "go back"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = WindowInsets.statusBars
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            TabRow(selectedTabIndex = Tabs.entries.indexOf(state.currentTab)) {
                Tabs.entries.forEach { tab ->
                    Tab(
                        selected = tab == state.currentTab,
                        onClick = { vm.changeTab(tab) },
                        icon = tab.icon,
                        text = tab.title
                    )
                }
            }
            state.currentTab.content(vm)
        }
    }
}