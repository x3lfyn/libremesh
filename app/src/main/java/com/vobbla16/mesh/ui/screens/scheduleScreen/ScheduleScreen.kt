package com.vobbla16.mesh.ui.screens.scheduleScreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vobbla16.mesh.R
import com.vobbla16.mesh.common.localDateTimeNow
import com.vobbla16.mesh.common.msToLocalDate
import com.vobbla16.mesh.common.toHumanStr
import com.vobbla16.mesh.domain.model.schedule.Activity
import com.vobbla16.mesh.ui.MainScaffoldController
import com.vobbla16.mesh.ui.Screens
import com.vobbla16.mesh.ui.screens.scheduleScreen.components.ScheduleBreakItem
import com.vobbla16.mesh.ui.screens.scheduleScreen.components.ScheduleLessonItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(navController: NavController, scaffoldController: MainScaffoldController) {
    val vm: ScheduleScreenViewModel = koinViewModel()
    val state = vm.viewState.value

    SideEffect {
        Log.d("RECOMPS", "SchedScreen recomposition occurred")
    }

    scaffoldController.uiState.value = scaffoldController.uiState.value.copy(topBar = {
        TopAppBar(title = {
            Text(
                text = state.schedule?.date?.toHumanStr(
                    LocalConfiguration.current
                ) ?: ""
            )
        }, actions = {
            IconButton(onClick = { vm.updateDatePickerOpened(true) }) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "calendar icon"
                )
            }
        })
    }, showBottomBar = true, fab = {})

    val vmActionsScope = rememberCoroutineScope()
    LaunchedEffect(key1 = vm.action) {
        vmActionsScope.launch {
            vm.action.onEach { action ->
                when (action) {
                    is ScheduleScreenAction.NavigateToLoginScreen -> {
                        navController.navigate(Screens.Login.route)
                    }
                }
            }.collect()
        }
    }

    navController.currentBackStackEntry?.savedStateHandle.let {
        LaunchedEffect(key1 = it) {
            if (it?.get<Boolean>("loggedIn") == true) {
                vm.afterLoggingIn()
            }
        }
    }

    Column {
        if (state.isLoading) {
            CircularProgressIndicator(
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )
        }

        state.error?.let { err ->
            Text(
                err,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        if (state.datePickerOpened) {
            val datePickerState = rememberDatePickerState()
            val confirmEnabled =
                remember { derivedStateOf { datePickerState.selectedDateMillis != null } }
            DatePickerDialog(onDismissRequest = {
                vm.updateDatePickerOpened(false)
            }, confirmButton = {
                TextButton(
                    onClick = {
                        vm.updateDate(
                            datePickerState.selectedDateMillis!!.msToLocalDate()
                        )
                        vm.updateDatePickerOpened(false)
                    }, enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            }, dismissButton = {
                TextButton(onClick = {
                    vm.updateDatePickerOpened(false)
                }) {
                    Text("Cancel")
                }
            }) {
                DatePicker(state = datePickerState)
            }
        }

        state.schedule?.let {
            if (it.activities.isNotEmpty()) {
                LazyColumn {
                    items(it.activities) { activity ->
                        when (activity) {
                            is Activity.Lesson -> {
                                ScheduleLessonItem(activity)
                            }

                            is Activity.Break -> {
                                ScheduleBreakItem(activity)
                            }
                        }
                    }
                    item {
                        Text(
                            text = it.summary,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (it.date == localDateTimeNow().date) stringResource(R.string.no_lessons_today)
                        else stringResource(R.string.no_lessons_that_day),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}