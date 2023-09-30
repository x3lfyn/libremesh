package com.vobbla16.mesh.ui.screens.marksScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import com.vobbla16.mesh.ui.screens.marksScreen.localState.SingleDayReport

@ExperimentalFoundationApi
data class MarksScreenState(
    val selectedTabIndex: Int,
    val openedSubjectsIndices: List<Int>,
    val dataGroupedByDate: List<SingleDayReport>?
)
