package com.vobbla16.mesh.ui.screens.subjectScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vobbla16.mesh.LocalMainVM
import com.vobbla16.mesh.R
import com.vobbla16.mesh.ui.commonComponents.genericHolderContainer.GenericHolderContainer
import com.vobbla16.mesh.ui.screens.destinations.LessonScreenDestination
import com.vobbla16.mesh.ui.screens.destinations.LoginScreenDestination
import com.vobbla16.mesh.ui.screens.lessonScreen.OpenTab
import com.vobbla16.mesh.ui.screens.marksScreen.components.ClassRatingItemCard
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
@Destination
fun SubjectScreen(
    navigator: DestinationsNavigator,
    subjectId: Long
) {
    val mainVM = LocalMainVM.current
    val vm: SubjectScreenViewModel = koinViewModel()
    val state = vm.viewState.value
    val ctx = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        mainVM.showBottomBar()
        vm.selectSubject(subjectId)
    }

    val vmActionsScope = rememberCoroutineScope()
    LaunchedEffect(key1 = vm.action) {
        vmActionsScope.launch {
            vm.action.onEach { action ->
                when (action) {
                    is SubjectScreenAction.NavigateToLoginScreen -> {
                        navigator.navigate(LoginScreenDestination)
                    }

                    SubjectScreenAction.FailedToOpenMark -> {
                        mainVM.viewState.value.snackbarHostState.showSnackbar(
                            ctx.getString(R.string.failed_to_open_lesson)
                        )
                    }

                    is SubjectScreenAction.OpenMarkInfo -> {
                        navigator.navigate(
                            LessonScreenDestination(
                                action.selector,
                                OpenTab.Marks
                            )
                        )
                    }
                }
            }.collect()
        }
    }

    val scope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    state.marks.data?.subjectName?.let { subjName ->
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
            GenericHolderContainer(
                holder = state.marks,
                onRefresh = { vm.onRefresh() },
                onRetry = { vm.onRetry() }
            ) { data ->
                val pagerState = rememberPagerState(pageCount = { data.periods.size + 1 })

                TabRow(selectedTabIndex = pagerState.targetPage) {
                    repeat(data.periods.size) { periodNum ->
                        Tab(
                            selected = pagerState.targetPage == periodNum,
                            onClick = { scope.launch { pagerState.animateScrollToPage(periodNum) } },
                            text = {
                                Text(text = data.periods[periodNum].title)
                            }
                        )
                    }
                    Tab(
                        selected = pagerState.targetPage == data.periods.size,
                        onClick = { scope.launch { pagerState.animateScrollToPage(data.periods.size) } },
                        text = {
                            Text(text = "Rating")
                        }
                    )
                }

                HorizontalPager(state = pagerState) { page ->
                    when {
                        page == data.periods.size -> {
                            GenericHolderContainer(
                                holder = state.rating,
                                onRefresh = { vm.onRefresh() },
                                onRetry = { vm.onRetry() }
                            ) { rating->
                                LazyColumn {
                                    items(rating) { personRating ->
                                        ClassRatingItemCard(
                                            item = personRating,
                                            modifier = Modifier.padding(6.dp)
                                        )
                                    }
                                }

                                if(rating.isEmpty()) {
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        Text(
                                            text = stringResource(R.string.subject_screen_no_rating),
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                    }
                                }
                            }
                        }

                        else -> {
                            PeriodTab(
                                period = data.periods[page],
                                onMarkClick = { vm.openMarkInfo(it) },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}