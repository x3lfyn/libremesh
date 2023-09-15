package com.vobbla16.mesh.ui.screens.marksScreen

import com.vobbla16.mesh.domain.model.marks.MarksSubjectModel
import com.vobbla16.mesh.ui.ViewState
import com.vobbla16.mesh.ui.screens.marksScreen.localState.SingleDayReport
import com.vobbla16.mesh.ui.screens.marksScreen.localState.toSingleDayReports

data class MarksScreenState(
    val dataGroupedBySubject: List<MarksSubjectModel>?,
    val isLoading: Boolean,
    val error: String?,
    val selectedTabIndex: Int,
    val openedSubjectsIndices: List<Int>
): ViewState {
    val dataGroupedByDate: List<SingleDayReport>? = dataGroupedBySubject?.toSingleDayReports()
}
