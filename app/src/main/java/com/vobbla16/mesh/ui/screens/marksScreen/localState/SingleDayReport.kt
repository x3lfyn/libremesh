package com.vobbla16.mesh.ui.screens.marksScreen.localState

import com.vobbla16.mesh.domain.model.marks.Mark
import com.vobbla16.mesh.domain.model.marks.MarksSubjectModel
import kotlinx.datetime.LocalDate

data class SingleDayReport(
    val date: LocalDate,
    val marks: List<MarkWithSubject>
)

data class MarkWithSubject(
    val subjectName: String,
    val mark: Mark
)

fun List<MarksSubjectModel>.toSingleDayReports(): List<SingleDayReport> {
    val marks: MutableList<MarkWithSubject> = mutableListOf()

    this.forEach { subj ->
        subj.periods.forEach { period ->
            marks += period.marks.map { MarkWithSubject(subj.subjectName, it) }
        }
    }

    return marks
        .groupBy { it.mark.date }
        .map { SingleDayReport(it.key, it.value) }
        .sortedBy { it.date }
        .reversed()
}