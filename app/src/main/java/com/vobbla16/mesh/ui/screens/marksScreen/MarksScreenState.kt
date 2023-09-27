package com.vobbla16.mesh.ui.screens.marksScreen

import com.vobbla16.mesh.ui.screens.marksScreen.localState.SingleDayReport

data class MarksScreenState(
    val selectedTabIndex: Int,
    val openedSubjectsIndices: List<Int>,
    val dataGroupedByDate: List<SingleDayReport>?
)
