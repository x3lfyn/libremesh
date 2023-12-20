package com.vobbla16.mesh

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vobbla16.mesh.ui.NavBarItems

@Composable
fun MainScaffold(
    navController: NavController,
    vm: MainActivityViewModel,
    content: @Composable () -> Unit
) {
    val state = vm.viewState.value

    Scaffold(
        bottomBar = {
            if (state.showBottomBar) {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    NavBarItems.values().forEach { item ->
                        val selected = currentDestination?.hierarchy?.any { it.route == item.screen.route } == true
                        NavigationBarItem(
                            selected = selected,
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
                            icon = if(selected) { item.activeIcon } else { item.inactiveIcon }
                        )
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(state.snackbarHostState) }, contentWindowInsets = WindowInsets.navigationBars
    ) {
        Box(modifier = Modifier.padding(it)) { content() }
    }
}