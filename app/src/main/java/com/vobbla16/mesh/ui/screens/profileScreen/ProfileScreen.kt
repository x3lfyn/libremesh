package com.vobbla16.mesh.ui.screens.profileScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.vobbla16.mesh.MainActivityViewModel
import com.vobbla16.mesh.ui.Screens
import com.vobbla16.mesh.ui.commonComponents.genericHolderContainer.GenericHolderContainer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, mainVM: MainActivityViewModel) {
    val vm: ProfileScreenViewModel = koinViewModel()
    val state = vm.viewState.value

    LaunchedEffect(key1 = null) {
        mainVM.showBottomBar()
    }

    if (state.dialogOpened) {
        AlertDialog(
            onDismissRequest = {
                if (!state.isLoggingOut) {
                    vm.updatedDialogOpened(false)
                }
            },
            icon = {
                Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "exit icon")
            },
            title = {
                Text(text = "Выйти")
            },
            text = {
                Text(text = "Будет произведён выход из аккаунта. После необходимо будет заново произвести авторизацию")
            },
            confirmButton = {
                TextButton(onClick = { vm.requestLogOut() }) {
                    Text(text = "Подтвердить")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { vm.updatedDialogOpened(false) },
                    enabled = !state.isLoggingOut
                ) {
                    Text(text = "Отклонить")
                }
            },
            properties = DialogProperties(usePlatformDefaultWidth = true)
        )
    }

    val vmActionsScope = rememberCoroutineScope()
    LaunchedEffect(key1 = vm.action) {
        vmActionsScope.launch {
            vm.action.onEach { action ->
                when (action) {
                    is ProfileScreenAction.NavigateToLoginScreen -> {
                        navController.navigate(Screens.Login.route)
                    }

                    is ProfileScreenAction.RestartAfterTokenReset -> {
                        navController.navigate(Screens.Login.route) {
                            popUpTo(Screens.Schedule.route)
                        }
                    }
                }
            }.collect()
        }
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Profile screen") },
                actions = {
                    IconButton(
                        onClick = {
                            vm.updatedDialogOpened(true)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "log out"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        contentWindowInsets = WindowInsets.statusBars,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        GenericHolderContainer(
            holder = state.childData,
            onRefresh = { vm.refreshData() },
            onRetry = { vm.retryOnError() },
            modifier = Modifier
                .padding(paddingValues)
        ) { profile ->
            Column(Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    text = "${profile.firstName} ${profile.middleName} ${profile.lastName}",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(6.dp)
                )

                @Composable
                fun OneLineData(first: String, second: String) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = first,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(4.dp, 1.dp)
                        )
                        Text(
                            text = second,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(4.dp, 1.dp)
                        )
                    }
                }

                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 4.dp)
                ) {
                    Text(
                        text = "Общая информация",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(4.dp, 2.dp)
                    )
                    Column(Modifier.padding(4.dp, 1.dp, 2.dp, 4.dp)) {
                        OneLineData(first = "Класс:", second = profile.className)
                        OneLineData(first = "Электропочта:", second = profile.email)
                        OneLineData(first = "Телефон:", second = profile.phone)
                        OneLineData(first = "СНИЛС:", second = profile.snils)
                    }
                }

                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 4.dp)
                ) {
                    Text(
                        text = "Школа",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(4.dp, 2.dp)
                    )
                    Column(Modifier.padding(4.dp, 1.dp, 2.dp, 4.dp)) {
                        Text(
                            text = profile.school.shortName,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(4.dp, 1.dp)
                        )
                        Text(
                            text = profile.school.name,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(4.dp, 1.dp)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        OneLineData(first = "Директор:", second = profile.school.principal)
                        OneLineData(first = "Телефон:", second = profile.school.phone)
                    }
                }

                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 4.dp)
                ) {
                    Text(
                        text = "Секции",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(4.dp, 2.dp)
                    )
                    Column(Modifier.padding(4.dp, 1.dp, 2.dp, 4.dp)) {
                        profile.sections.forEach { section ->
                            Text(
                                text = "· " + section.name,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(4.dp, 1.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}