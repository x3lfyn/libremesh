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
import androidx.compose.material.icons.automirrored.filled.ExitToApp
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vobbla16.mesh.LocalMainVM
import com.vobbla16.mesh.R
import com.vobbla16.mesh.ui.commonComponents.SpoilerText
import com.vobbla16.mesh.ui.commonComponents.genericHolderContainer.GenericHolderContainer
import com.vobbla16.mesh.ui.screens.destinations.LoginScreenDestination
import com.vobbla16.mesh.ui.screens.destinations.ScheduleScreenDestination
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun ProfileScreen(navigator: DestinationsNavigator) {
    val mainVM = LocalMainVM.current
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
                Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "exit icon")
            },
            title = {
                Text(text = stringResource(R.string.profile_screen_logout_title))
            },
            text = {
                Text(text = stringResource(R.string.profile_screen_logout_description))
            },
            confirmButton = {
                TextButton(onClick = { vm.requestLogOut() }) {
                    Text(text = stringResource(R.string.profile_screen_logout_confirm))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { vm.updatedDialogOpened(false) },
                    enabled = !state.isLoggingOut
                ) {
                    Text(text = stringResource(R.string.profile_screen_logout_cancel))
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
                        navigator.navigate(LoginScreenDestination)
                    }

                    is ProfileScreenAction.RestartAfterTokenReset -> {
                        navigator.navigate(LoginScreenDestination) {
                            popUpTo(ScheduleScreenDestination.route)
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
                title = { Text(text = stringResource(R.string.profile_screen_title)) },
                actions = {
                    IconButton(
                        onClick = {
                            vm.updatedDialogOpened(true)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
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
                fun OneLineData(first: String, second: String, isSecret: Boolean = false) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = first,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(4.dp, 1.dp)
                        )
                        if (isSecret) {
                            SpoilerText(modifier = Modifier.padding(4.dp, 1.dp)) {
                                Text(
                                    text = second,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        } else {
                            Text(
                                text = second,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(4.dp, 1.dp)
                            )
                        }
                    }
                }

                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.profile_screen_section_common_info),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(4.dp, 2.dp)
                    )
                    Column(Modifier.padding(4.dp, 1.dp, 2.dp, 4.dp)) {
                        OneLineData(first = stringResource(R.string.profile_screen_classname), second = profile.className)
                        OneLineData(first = stringResource(R.string.profile_screen_email), second = profile.email, true)
                        OneLineData(first = stringResource(R.string.profile_screen_telephone), second = profile.phone, true)
                        OneLineData(first = stringResource(R.string.profile_screen_snils), second = profile.snils, true)
                    }
                }

                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.profile_screen_section_school),
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
                        OneLineData(first = stringResource(R.string.profile_screen_school_head), second = profile.school.principal)
                        OneLineData(first = stringResource(R.string.profile_screen_school_telephone), second = profile.school.phone)
                    }
                }

                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.profile_screen_section_sections),
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