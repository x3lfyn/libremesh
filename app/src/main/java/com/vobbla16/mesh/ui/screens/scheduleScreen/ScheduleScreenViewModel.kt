package com.vobbla16.mesh.ui.screens.scheduleScreen

import androidx.lifecycle.viewModelScope
import com.vobbla16.mesh.common.OrLoading
import com.vobbla16.mesh.common.Resource
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
        isRefreshing = false,
        error = null,
        datePickerOpened = false
    )

    fun updateDate(date: LocalDate) = getSchedule(date, true)
    fun updateDatePickerOpened(opened: Boolean) = setState { copy(datePickerOpened = opened) }
    fun afterLoggingIn() = getSchedule(localDateTimeNow().date, false)
    fun updateData(refresh: Boolean) =
        getSchedule(viewState.value.schedule?.date ?: localDateTimeNow().date, refresh)

    init {
        getSchedule(localDateTimeNow().date, false)
    }

    private fun getSchedule(date: LocalDate, refresh: Boolean) = viewModelScope.launch {
        getScheduleUseCase(date)
            .onEach {
                when (it) {
                    is OrLoading.Loading -> {
                        if (refresh) setState { copy(isRefreshing = true, error = null) }
                        else setState { copy(isLoading = true, error = null, schedule = null) }
                    }

                    is OrLoading.Data -> {
                        when (it.res) {
                            is Resource.Ok -> {
                                if (refresh) setState {
                                    copy(
                                        schedule = it.res.data,
                                        isRefreshing = false,
                                        error = null
                                    )
                                }
                                else setState {
                                    copy(
                                        schedule = it.res.data,
                                        isLoading = false,
                                        error = null
                                    )
                                }
                            }

                            is Resource.Err -> {
                                if (refresh) setState {
                                    copy(
                                        error = it.res.e,
                                        isRefreshing = false
                                    )
                                }
                                else setState { copy(error = it.res.e, isLoading = false) }
                            }

                            is Resource.NotLoggedIn -> {
                                setAction { ScheduleScreenAction.NavigateToLoginScreen }
                            }
                        }
                    }
                }
            }.collect()
    }
}