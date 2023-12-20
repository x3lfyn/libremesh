package com.vobbla16.mesh.ui.screens.scheduleScreen

import androidx.lifecycle.viewModelScope
import com.vobbla16.mesh.common.localDateTimeNow
import com.vobbla16.mesh.domain.use_case.GetScheduleUseCase
import com.vobbla16.mesh.ui.BaseViewModel
import com.vobbla16.mesh.ui.genericHolder.GenericHolder
import com.vobbla16.mesh.ui.genericHolder.LoadingState
import com.vobbla16.mesh.ui.genericHolder.processDataFromUseCase

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class ScheduleScreenViewModel(private val getScheduleUseCase: GetScheduleUseCase) :
    BaseViewModel<ScheduleScreenState, ScheduleScreenAction>() {

    override fun setInitialState() = ScheduleScreenState(
        datePickerOpened = false,
        selectedDate = localDateTimeNow().date,
        showBottomSheet = false,
        selectedLesson = null,
        lessonInfo = GenericHolder(),
        scheduleData = GenericHolder()
    )

    fun updateDate(date: LocalDate) {
        setAction { ScheduleScreenAction.UpdateDataPickerState(date) }
        setState { copy(selectedDate = date) }
        getSchedule(true)
    }

    fun updateDatePickerOpened(opened: Boolean) =
        setState { copy(datePickerOpened = opened) }

    fun afterLoggingIn() = getSchedule(false)
    fun updateData(refresh: Boolean) =
        getSchedule(refresh)

    init {
        getSchedule(false)
    }

    private fun getSchedule(refresh: Boolean) = viewModelScope.launch {
        processDataFromUseCase(
            useCase = getScheduleUseCase(viewState.value.selectedDate),
            resultReducer = { this },
            loadingType = LoadingState.fromBool(refresh),
            stateProperty = ScheduleScreenState::scheduleData,
            onNotLoggedIn = { setAction { ScheduleScreenAction.NavigateToLoginScreen } },
        )
    }

    private fun getLessonInfo() = viewModelScope.launch {
        setState { copy(lessonInfo = viewState.value.lessonInfo.copy(loading = LoadingState.Load)) }
        delay(1000L)
        setState {
            copy(
                lessonInfo = viewState.value.lessonInfo.copy(
                    data = "123",
                    loading = LoadingState.Nothing
                )
            )
        }
    }
}