package com.vobbla16.mesh.ui.screens.scheduleScreen

import androidx.lifecycle.viewModelScope
import com.vobbla16.mesh.common.ResourceOrNotLoggedIn
import com.vobbla16.mesh.common.localDateTimeNow
import com.vobbla16.mesh.domain.use_case.GetScheduleUseCase
import com.vobbla16.mesh.ui.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class ScheduleScreenViewModel(private val getScheduleUseCase: GetScheduleUseCase) :
    BaseViewModel<ScheduleScreenState, ScheduleScreenAction>() {

    override fun setInitialState() = ScheduleScreenState(
        schedule = null,
        isLoading = true,
        error = null,
        datePickerOpened = false
    )

    fun updateDate(date: LocalDate) = getSchedule(date)
    fun updateDatePickerOpened(opened: Boolean) = setState { copy(datePickerOpened = opened) }
    fun afterLoggingIn() = getSchedule(localDateTimeNow().date)

    init {
        getSchedule(localDateTimeNow().date)
    }

    private fun getSchedule(date: LocalDate) = viewModelScope.launch {
        getScheduleUseCase(date)
            .onEach {
                when (it) {
                    is ResourceOrNotLoggedIn.Success -> {
                        setState { copy(schedule = it.data, isLoading = false, error = null) }
                    }

                    is ResourceOrNotLoggedIn.Loading -> {
                        setState { copy(isLoading = true) }
                    }

                    is ResourceOrNotLoggedIn.Error -> {
                        setState { copy(error = it.message, isLoading = false) }
                    }

                    is ResourceOrNotLoggedIn.NotLoggedIn -> {
                        setAction { ScheduleScreenAction.NavigateToLoginScreen }
                    }
                }
            }.collect()
    }
}