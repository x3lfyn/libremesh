package com.vobbla16.mesh.ui.screens.lessonScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vobbla16.mesh.LocalMainVM
import com.vobbla16.mesh.R
import com.vobbla16.mesh.common.toShortLocalizedStr
import com.vobbla16.mesh.domain.model.common.LessonSelector
import com.vobbla16.mesh.ui.screens.destinations.LoginScreenDestination
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
@Destination
fun LessonScreen(
    navigator: DestinationsNavigator,
    lessonSelector: LessonSelector,
    openTab: OpenTab = OpenTab.Description
) {
    val mainVM = LocalMainVM.current
    val vm: LessonScreenViewModel = koinViewModel()
    val state = vm.viewState.value
    val ctx = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        mainVM.showBottomBar()
        vm.setSelectedLesson(lessonSelector)
        vm.changeTab(openTab.tab)
    }

    val vmActionsScope = rememberCoroutineScope()
    LaunchedEffect(key1 = vm.action) {
        vmActionsScope.launch {
            vm.action.onEach { action ->
                when (action) {
                    is LessonScreenAction.NavigateToLoginScreen -> {
                        navigator.navigate(LoginScreenDestination)
                    }

                    is LessonScreenAction.ErrorHomeworkMarkDone -> {
                        mainVM.viewState.value.snackbarHostState.showSnackbar(
                            ctx.getString(
                                R.string.failed_to_mark_homework_done,
                                action.err
                            )
                        )
                    }

                    is LessonScreenAction.ErrorHomeworkMarkUndone -> {
                        mainVM.viewState.value.snackbarHostState.showSnackbar(
                            ctx.getString(
                                R.string.failed_to_mark_homework_undone,
                                action.err
                            )
                        )
                    }
                }
            }.collect()
        }
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    state.lessonInfo.data?.subjectName?.let { subjName ->
                        Text(text = subjName, modifier = Modifier.basicMarquee())
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
        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            state.lessonInfo.data?.let { model ->
                Text(
                    text = model.beginTime.date.toShortLocalizedStr(LocalConfiguration.current),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = buildAnnotatedString {
                        append(model.beginTime.time.toString())
                        appendInlineContent("arrow")
                        append("${model.durationMinutes()} ${stringResource(id = R.string.minutes)}")
                        appendInlineContent("arrow")
                        append(model.endTime.time.toString())
                    },
                    inlineContent = mapOf("arrow" to InlineTextContent(
                        Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "arrow icon"
                        )
                    }),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } ?: run {
                LinearProgressIndicator(
                    modifier = Modifier
                        .width(150.dp)
                        .padding(0.dp, 20.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

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