package com.vobbla16.mesh.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

//data class MainScaffoldState(
//    val topBar: MutableState<@Composable () -> Unit> = mutableStateOf({}),
//    val showBottomBar: MutableState<Boolean> = mutableStateOf(false),
//    val fab: MutableState<@Composable () -> Unit> = mutableStateOf({}),
//    val fabPosition: MutableState<FabPosition> = mutableStateOf(FabPosition.End),
//
//    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
//)
data class MainScaffoldState(
    val topBar: (@Composable () -> Unit)? = null,
    val showBottomBar: Boolean = false,
    val fab: @Composable () -> Unit = {},
    val fabPosition: FabPosition = FabPosition.End
)

data class MainScaffoldController(
    val uiState: MutableState<MainScaffoldState>,
    val snackbarHostState: SnackbarHostState = SnackbarHostState()
)

@Composable
fun MainScaffold(
    navController: NavController,
    content: @Composable (MainScaffoldController) -> Unit
) {
    val scaffoldState = remember {
        mutableStateOf(MainScaffoldState())
    }
    val scaffoldController = MainScaffoldController(scaffoldState)

    Scaffold(
        topBar = { scaffoldState.value.topBar?.invoke() },
        bottomBar = {
            if (scaffoldState.value.showBottomBar) {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    NavBarItems.values().forEach { item ->
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any { it.route == item.screen.route } == true,
                            label = { Text(text = item.label) },
                            onClick = {
                                navController.navigate(item.screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = item.icon
                        )
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(scaffoldController.snackbarHostState) },
        floatingActionButton = scaffoldState.value.fab,
        floatingActionButtonPosition = scaffoldState.value.fabPosition
    ) {
        Box(modifier = Modifier.padding(it)) { content(scaffoldController) }
    }
}