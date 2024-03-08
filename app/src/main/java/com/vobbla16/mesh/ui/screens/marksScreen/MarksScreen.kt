package com.vobbla16.mesh.ui.screens.marksScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vobbla16.mesh.LocalMainVM
import com.vobbla16.mesh.R
import com.vobbla16.mesh.ui.commonComponents.genericHolderContainer.GenericHolderContainer
import com.vobbla16.mesh.ui.screens.destinations.LessonScreenDestination
import com.vobbla16.mesh.ui.screens.destinations.LoginScreenDestination
import com.vobbla16.mesh.ui.screens.lessonScreen.OpenTab
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@ExperimentalFoundationApi
@Destination
fun MarksScreen(navigator: DestinationsNavigator) {
    val mainVM = LocalMainVM.current
    val vm: MarksScreenViewModel = koinViewModel()
    val state = vm.viewState.value

    LaunchedEffect(key1 = null) {
        mainVM.showBottomBar()
    }

    val vmActionsScope = rememberCoroutineScope()
    LaunchedEffect(key1 = vm.action) {
        vmActionsScope.launch {
            vm.action.onEach { action ->
                when (action) {
                    is MarksScreenAction.NavigateToLoginScreen -> {
                        navigator.navigate(LoginScreenDestination)
                    }

                    MarksScreenAction.FailedToOpenInfo -> {
                        mainVM.viewState.value.snackbarHostState.showSnackbar("Failed to get information")
                    }

                    is MarksScreenAction.OpenMarkInfo -> {
                        navigator.navigate(
                            LessonScreenDestination(
                                action.lessonSelector,
                                OpenTab.Marks
                            )
                        )
                    }
                }
            }.collect()
        }
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var menuExpanded by remember {
        mutableStateOf(false)
    }

    val pagerState = rememberPagerState(pageCount = { Tabs.entries.size })
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Marks"
                )
            }, actions = {
                if (pagerState.currentPage == 2) {
                    IconButton(onClick = {
                        vm.toggleAnonymousRating()
                    }) {
                        if (state.anonymousRating) {
                            Icon(
                                painter = painterResource(id = R.drawable.visibility_off),
                                contentDescription = "hidden icon"
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.visibility),
                                contentDescription = "visible icon"
                            )
                        }
                    }
                }

                IconButton(onClick = { menuExpanded = !menuExpanded }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "menu icon"
                    )
                }

                DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text(text = "Arrange by") },
                        onClick = {},
                        enabled = false
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "By date")
                        },
                        trailingIcon = {
                            if (pagerState.currentPage == 0)
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "selected"
                                )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "calendar icon"
                            )
                        },
                        onClick = { scope.launch { pagerState.animateScrollToPage(0) } }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "By subject")
                        },
                        trailingIcon = {
                            if (pagerState.currentPage == 1)
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "selected"
                                )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.subject_24dp),
                                contentDescription = "calendar icon"
                            )
                        },
                        onClick = { scope.launch { pagerState.animateScrollToPage(1) } }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "Rating")
                        },
                        trailingIcon = {
                            if (pagerState.currentPage == 2)
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "selected"
                                )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "star icon"
                            )
                        },
                        onClick = { scope.launch { pagerState.animateScrollToPage(2) } }
                    )
                }
            }, scrollBehavior = scrollBehavior)
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = WindowInsets.statusBars
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            //        TabRow(
            //            selectedTabIndex = pagerState.currentPage,
            //            tabs = {
            //                Tabs.values().forEachIndexed { index, tab ->
            //                    Tab(
            //                        selected = index == pagerState.currentPage,
            //                        onClick = {
            //                            scope.launch {
            //                                pagerState.animateScrollToPage(index)
            //                            }
            //                        },
            //                        icon = tab.icon,
            //                        text = { Text(text = tab.title) }
            //                    )
            //                }
            //            },
            //            indicator = {
            //                TabRowDefaults.Indicator(Modifier.tabIndicatorOffset(it[pagerState.currentPage]))
            //            }
            //        )
            GenericHolderContainer(
                holder = state.marksData,
                onRefresh = { vm.refreshData() },
                onRetry = { vm.retryOnError() }
            ) {
                HorizontalPager(state = pagerState) { page ->
                    Tabs.entries[page].subscreen(vm)
                }
            }
        }
    }
}