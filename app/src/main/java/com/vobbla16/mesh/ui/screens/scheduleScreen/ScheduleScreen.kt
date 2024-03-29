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
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.vobbla16.mesh.LocalMainVM
import com.vobbla16.mesh.R
import com.vobbla16.mesh.common.localDateTimeNow
import com.vobbla16.mesh.common.secsToLocalTime
import com.vobbla16.mesh.common.toEpochSecond
import com.vobbla16.mesh.common.toHumanStr
import com.vobbla16.mesh.domain.model.schedule.Activity
import com.vobbla16.mesh.domain.model.schedule.toLessonSelector
import com.vobbla16.mesh.ui.commonComponents.genericHolderContainer.GenericHolderContainer
import com.vobbla16.mesh.ui.screens.destinations.LessonScreenDestination
import com.vobbla16.mesh.ui.screens.destinations.LoginScreenDestination
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
@RootNavGraph(start = true)
@Destination
fun ScheduleScreen(
    navigator: DestinationsNavigator,
    loginResultRecipient: ResultRecipient<LoginScreenDestination, Boolean>
) {
    val mainVM = LocalMainVM.current
    val vm: ScheduleScreenViewModel = koinViewModel()
    val state = vm.viewState.value

    LaunchedEffect(key1 = state.scheduleData.data?.date) {
        mainVM.showBottomBar()
    }

    val datePickerState = rememberDatePickerState()
    val confirmEnabled =
        remember { derivedStateOf { datePickerState.selectedDateMillis != null } }

    val vmActionsScope = rememberCoroutineScope()
    LaunchedEffect(key1 = vm.action) {
        vmActionsScope.launch {
            vm.action.onEach { action ->
                when (action) {
                    is ScheduleScreenAction.NavigateToLoginScreen -> {
                        navigator.navigate(LoginScreenDestination)
                    }

                    is ScheduleScreenAction.UpdateDataPickerState -> {
                        datePickerState.selectedDateMillis = action.newDate.toEpochSecond() * 1000
                    }
                }
            }.collect()
        }
    }

    loginResultRecipient.onNavResult {
        when (it) {
            is NavResult.Value -> {
                vm.afterLoggingIn()
            }

            else -> {}
        }
    }

    val currentTime = localDateTimeNow().time

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
                            datePickerState.selectedDateMillis =
                                localDateTimeNow().toInstant(TimeZone.currentSystemDefault()).epochSeconds * 1000
                            vm.updateDate(localDateTimeNow().date)
                        }
                    ) {
                        Text(stringResource(R.string.schedule_screen_return_today_btn))
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
                                    (datePickerState.selectedDateMillis!! / 1000).secsToLocalTime().date
                                )
                                vm.updateDatePickerOpened(false)
                            }, enabled = confirmEnabled.value
                        ) {
                            Text(stringResource(R.string.schedule_screen_datepicker_ok_btn))
                        }
                    }, dismissButton = {
                        TextButton(onClick = {
                            vm.updateDatePickerOpened(false)
                        }) {
                            Text(stringResource(R.string.schedule_screen_datepicker_cancel_btn))
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
                                    val highlighted =
                                        (state.selectedDate == localDateTimeNow().date) and
                                                (currentTime >= activity.beginTime) and
                                                (currentTime < activity.endTime)

                                    ScheduleLessonItem(
                                        activity = activity,
                                        highlighted = highlighted
                                    ) {
                                        navigator.navigate(
                                            LessonScreenDestination(
                                                activity.toLessonSelector()
                                            )
                                        )
                                    }
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
