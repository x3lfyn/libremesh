package com.vobbla16.mesh.domain.model.schedule

import kotlinx.datetime.LocalDate

data class ScheduleModel(
    val date: LocalDate,
    val summary: String,
    val activities: List<Activity>
)
