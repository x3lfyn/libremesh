package com.vobbla16.mesh.ui.screens.homeworkScreen

import androidx.paging.PagingData
import com.vobbla16.mesh.domain.model.homework.HomeworkItemsForDate
import com.vobbla16.mesh.ui.ViewState
import kotlinx.coroutines.flow.Flow

data class HomeworkScreenState(
    val pagingFlow: Flow<PagingData<HomeworkItemsForDate>>?,
    val isLoading: Boolean,
    val error: String?
): ViewState
