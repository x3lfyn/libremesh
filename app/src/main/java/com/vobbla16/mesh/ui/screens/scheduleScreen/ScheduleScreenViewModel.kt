package com.vobbla16.mesh.ui.screens.scheduleScreen

import androidx.lifecycle.viewModelScope
import com.vobbla16.mesh.common.localDateTimeNow
import com.vobbla16.mesh.domain.model.schedule.ScheduleModel
import com.vobbla16.mesh.domain.use_case.GetScheduleUseCase
import com.vobbla16.mesh.ui.BaseViewModel
import com.vobbla16.mesh.ui.genericHolder.LoadingState
import com.vobbla16.mesh.ui.genericHolder.processDataFromUseCase
import com.vobbla16.mesh.ui.reduceOtherState
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class ScheduleScreenViewModel(private val getScheduleUseCase: GetScheduleUseCase) :
    BaseViewModel<ScheduleModel, ScheduleScreenState, ScheduleScreenAction>() {

    override fun setInitialState() = ScheduleScreenState(
        datePickerOpened = false
    )

    fun updateDate(date: LocalDate) = getSchedule(date, true)
    fun updateDatePickerOpened(opened: Boolean) =
        setState { reduceOtherState { copy(datePickerOpened = opened) } }

    fun afterLoggingIn() = getSchedule(localDateTimeNow().date, false)
    fun updateData(refresh: Boolean) =
        getSchedule(viewState.value.data?.date ?: localDateTimeNow().date, refresh)

    init {
        getSchedule(localDateTimeNow().date, false)
    }

    private fun getSchedule(date: LocalDate, refresh: Boolean) = viewModelScope.launch {
        processDataFromUseCase(
            useCase = getScheduleUseCase(date),
            resultReducer = { this },
            loadingType = if (refresh) LoadingState.Refresh else LoadingState.Load,
            onNotLoggedIn = { setAction { ScheduleScreenAction.NavigateToLoginScreen } })
    }
}