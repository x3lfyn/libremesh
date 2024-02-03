package com.vobbla16.mesh.ui.screens.marksScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import com.vobbla16.mesh.domain.model.classmates.ClassmateModel
import com.vobbla16.mesh.domain.model.marks.MarksSubjectModel
import com.vobbla16.mesh.ui.ViewState
import com.vobbla16.mesh.ui.genericHolder.GenericHolder
import com.vobbla16.mesh.ui.screens.marksScreen.localState.SingleDayReport

@ExperimentalFoundationApi
data class MarksScreenState(
    val marksData: GenericHolder<List<MarksSubjectModel>>,
    val classmates: GenericHolder<List<ClassmateModel>>,

    val selectedTabIndex: Int,
    val openedSubjectsIndices: List<Int>,
    val dataGroupedByDate: List<SingleDayReport>?
): ViewState
