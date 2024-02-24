package com.vobbla16.mesh.ui.screens.scheduleScreen

import com.vobbla16.mesh.domain.model.schedule.ScheduleModel
import com.vobbla16.mesh.ui.ViewState
import com.vobbla16.mesh.ui.genericHolder.GenericHolder
import kotlinx.datetime.LocalDate

data class ScheduleScreenState(
    val scheduleData: GenericHolder<ScheduleModel>,

    val datePickerOpened: Boolean,
    val selectedDate: LocalDate
): ViewState