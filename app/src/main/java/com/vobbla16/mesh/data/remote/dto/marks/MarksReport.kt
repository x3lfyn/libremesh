package com.vobbla16.mesh.data.remote.dto.marks


import com.vobbla16.mesh.common.nonIsoToLocalDate
import com.vobbla16.mesh.domain.model.marks.GradeType
import com.vobbla16.mesh.domain.model.marks.Mark
import com.vobbla16.mesh.domain.model.marks.MarkValue
import com.vobbla16.mesh.domain.model.marks.MarksSubjectModel
import com.vobbla16.mesh.domain.model.marks.Period
import kotlinx.datetime.toLocalDate


fun List<MarksReportItemDto>.toDomain(): List<MarksSubjectModel> = this.map { subj ->
    MarksSubjectModel(
        subjectName = subj.subjectName,
        average = MarkValue(subj.avgFive.toFloat(), subj.avgHundred.toFloat()),
        yearMark = subj.yearMark?.toInt(),
        examMark = subj.examMark?.toInt(),
        attestationMark = subj.attestationMark?.toInt(),
        periods = subj.periods.map { period ->
            Period(
                name = period.name,
                average = MarkValue(period.avgFive.toFloat(), period.avgHundred.toFloat()),
                finalMark = period.finalMark?.toInt(),
                startDate = period.startIso.toLocalDate(),
                endDate = period.endIso.toLocalDate(),
                isYearMark = period.isYearMark,
                marks = period.marks.map { mark ->
                    Mark(
                        comment = mark.comment,
                        controlFormName = mark.controlFormName,
                        date = mark.date.nonIsoToLocalDate(),
                        gradeType = GradeType.values().find { it.text == mark.gradeSystemType }!!,
                        isPoint = mark.isPoint,
                        pointDate = mark.pointDate?.nonIsoToLocalDate(),
                        topic = mark.topicName,
                        value = MarkValue(
                            mark.values[0].five.toFloat(),
                            mark.values[0].hundred.toFloat()
                        ),
                        weight = mark.weight
                    )
                }
            )
        }
    )
}