package com.vobbla16.mesh.ui.screens.subjectScreen

import com.vobbla16.mesh.domain.model.subjectMarks.SubjectMarksModel
import com.vobbla16.mesh.ui.ViewState
import com.vobbla16.mesh.ui.genericHolder.GenericHolder

data class SubjectScreenState(
    val subjectId: Long?,
    val marks: GenericHolder<SubjectMarksModel>
): ViewState
