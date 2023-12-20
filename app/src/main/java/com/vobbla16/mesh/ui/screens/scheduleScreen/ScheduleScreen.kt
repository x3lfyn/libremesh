package com.vobbla16.mesh.ui.screens.scheduleScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vobbla16.mesh.MainActivityViewModel
import com.vobbla16.mesh.R
import com.vobbla16.mesh.common.localDateTimeNow
import com.vobbla16.mesh.common.msToLocalDate
import com.vobbla16.mesh.common.toEpochSecond
import com.vobbla16.mesh.common.toHumanStr
import com.vobbla16.mesh.domain.model.schedule.Activity
import com.vobbla16.mesh.ui.Screens
import com.vobbla16.mesh.ui.commonComponents.genericHolderContainer.GenericHolderContainer
import com.vobbla16.mesh.ui.screens.scheduleScreen.components.DayOfWeekPicker
import com.vobbla16.mesh.ui.screens.scheduleScreen.components.ScheduleBreakItem
import com.vobbla16.mesh.ui.screens.scheduleScreen.components.ScheduleLessonItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(navController: NavController, mainVM: MainActivityViewModel) {
    val vm: ScheduleScreenViewModel = koinViewModel()
    val state = vm.viewState.value

    LaunchedEffect(key1 = state.scheduleData.data?.date) {
        mainVM.showBottomBar()
    }

    val datePickerState = remember {
        DatePickerState(
            initialDisplayMode = DisplayMode.Picker,
            yearRange = DatePickerDefaults.YearRange,
            initialSelectedDateMillis = null,
            initialDisplayedMonthMillis = null
        )
    }
    val confirmEnabled =
        remember { derivedStateOf { datePickerState.selectedDateMillis != null } }

    val vmActionsScope = rememberCoroutineScope()
    LaunchedEffect(key1 = vm.action) {
        vmActionsScope.launch {
            vm.action.onEach { action ->
                when (action) {
                    is ScheduleScreenAction.NavigateToLoginScreen -> {
                        navController.navigate(Screens.Login.route)
                    }

                    is ScheduleScreenAction.UpdateDataPickerState -> {
                        datePickerState.setSelection(action.newDate.toEpochSecond() * 1000)
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

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = state.selectedDate.toHumanStr(
                        LocalConfiguration.current
                    )
                )
            }, actions = {
                if (state.selectedDate != localDateTimeNow().date) {
                    TextButton(
                        onClick = {
                            datePickerState.setSelection(localDateTimeNow().toInstant(TimeZone.currentSystemDefault()).epochSeconds * 1000)
                            vm.updateDate(localDateTimeNow().date)
                        }
                    ) {
                        Text("Сегодня")
                    }
                }
                IconButton(onClick = { vm.updateDatePickerOpened(true) }) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "calendar icon"
                    )
                }
            }, scrollBehavior = scrollBehavior)
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = WindowInsets.statusBars
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            GenericHolderContainer(
                holder = state.scheduleData,
                onRefresh = { vm.updateData(true) },
                onRetry = { vm.updateData(false) },
                modifier = Modifier.weight(1f)
            ) { data ->
                if (state.datePickerOpened) {
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

                if (data.activities.isNotEmpty()) {
                    LazyColumn {
                        items(data.activities) { activity ->
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
                                text = data.summary,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()), // need scroll so pull to refresh can function correctly
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (data.date == localDateTimeNow().date) stringResource(R.string.no_lessons_today)
                            else stringResource(R.string.no_lessons_that_day),
                            style = MaterialTheme.typography.headlineSmall
                        )

                    }
                }
            }
            DayOfWeekPicker(
                selectedDay = state.selectedDate
            ) {
                vm.updateDate(it)
            }
        }
    }
}
