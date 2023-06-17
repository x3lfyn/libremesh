package com.vobbla16.mesh.domain.model.marks

data class MarksSubject(
    val subjectName: String,

    val average: MarkValue,

    val yearMark: Int?,
    val examMark: Int?,
    val attestationMark: Int?,

    val periods: List<Period>
)
