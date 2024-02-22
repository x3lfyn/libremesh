package com.vobbla16.mesh

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import com.vobbla16.mesh.ui.NavBarItems
import com.vobbla16.mesh.ui.screens.NavGraphs
import com.vobbla16.mesh.ui.screens.appCurrentDestinationAsState
import com.vobbla16.mesh.ui.screens.destinations.Destination
import com.vobbla16.mesh.ui.screens.startAppDestination

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScaffold(
    navController: NavController,
    content: @Composable () -> Unit
) {
    val state = LocalMainVM.current.viewState.value

    Scaffold(
        bottomBar = {
            if (state.showBottomBar) {
                NavigationBar {
                    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
                        ?: NavGraphs.root.startAppDestination
                    NavBarItems.entries.forEach { item ->
                        val selected = currentDestination == item.screen
                        NavigationBarItem(
                            selected = selected,
                            label = { Text(text = item.label) },
                            onClick = {
                                navController.navigate(item.screen) {
                                    launchSingleTop = true
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