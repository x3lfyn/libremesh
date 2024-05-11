package com.vobbla16.mesh.domain.model.subjectMarks

data class SubjectMarksModel(
    val subjectId: Long,
    val subjectName: String,
    val periods: List<Period>
)
