package com.vobbla16.mesh.ui.screens.marksScreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.vobbla16.mesh.MainActivityViewModel
import com.vobbla16.mesh.common.toText
import com.vobbla16.mesh.ui.Screens
import com.vobbla16.mesh.ui.commonComponents.genericHolderContainer.GenericHolderContainer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
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

    Column {
        TabRow(selectedTabIndex = state.otherState.selectedTabIndex, tabs = {
            Tabs.values().forEachIndexed { index, tab ->
                Tab(
                    selected = index == state.otherState.selectedTabIndex,
                    onClick = { vm.switchTab(index) },
                    icon = tab.icon,
                    text = { Text(text = tab.title) }
                )
            }
        })
        GenericHolderContainer(
            holder = state.dataState,
            onRefresh = { vm.refreshData() },
            onRetry = { vm.retryOnError() }
        ) {
            Tabs.values()[state.otherState.selectedTabIndex].subscreen(vm)
        }
    }
}