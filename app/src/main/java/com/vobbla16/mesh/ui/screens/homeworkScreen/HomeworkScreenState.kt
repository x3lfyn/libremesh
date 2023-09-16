package com.vobbla16.mesh.ui.screens.homeworkScreen

import com.vobbla16.mesh.domain.model.homework.HomeworkItemsForDateModel
import com.vobbla16.mesh.ui.ViewState

data class HomeworkScreenState(
    val data: List<HomeworkItemsForDateModel>?,
    val isLoading: Boolean,
    val error: String?
): ViewState
