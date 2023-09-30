package com.vobbla16.mesh.ui.screens.marksScreen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.vobbla16.mesh.MainActivityViewModel
import com.vobbla16.mesh.ui.Screens
import com.vobbla16.mesh.ui.commonComponents.genericHolderContainer.GenericHolderContainer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
@ExperimentalFoundationApi
fun MarksScreen(navController: NavController, mainVM: MainActivityViewModel) {
    val vm: MarksScreenViewModel = koinViewModel()
    val state = vm.viewState.value

    LaunchedEffect(key1 = null) {
        mainVM.showBottomBar()
    }

    SideEffect {
        Log.d("RECOMPS", "MarksScreen recomposition occurred")
    }

    val vmActionsScope = rememberCoroutineScope()
    LaunchedEffect(key1 = vm.action) {
        vmActionsScope.launch {
            vm.action.onEach { action ->
                when (action) {
                    is MarksScreenAction.NavigateToLoginScreen -> {
                        navController.navigate(Screens.Login.route)
                    }
                }
            }.collect()
        }
    }

    val pagerState = rememberPagerState(pageCount = { Tabs.values().size })
    val scope = rememberCoroutineScope()
    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            tabs = {
                Tabs.values().forEachIndexed { index, tab ->
                    Tab(
                        selected = index == pagerState.currentPage,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        icon = tab.icon,
                        text = { Text(text = tab.title) }
                    )
                }
            },
            indicator = {
                TabRowDefaults.Indicator(Modifier.tabIndicatorOffset(it[pagerState.currentPage]))
            }
        )
        GenericHolderContainer(
            holder = state.dataState,
            onRefresh = { vm.refreshData() },
            onRetry = { vm.retryOnError() }
        ) {
            HorizontalPager(state = pagerState) { page ->
                Tabs.values()[page].subscreen(vm)
            }
        }
    }
}