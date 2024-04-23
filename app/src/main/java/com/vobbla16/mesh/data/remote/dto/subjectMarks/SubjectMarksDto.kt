package com.vobbla16.mesh.data.remote.dto.subjectMarks


import com.vobbla16.mesh.domain.model.subjectMarks.Mark
import com.vobbla16.mesh.domain.model.subjectMarks.SubjectMarksModel
import kotlinx.datetime.toLocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubjectMarksDto(
    @SerialName("average")
    val average: String,
    @SerialName("average_by_all")
    val averageByAll: String,
    @SerialName("dynamic")
    val `dynamic`: String,
    @SerialName("periods")
    val periods: List<Period>,
    @SerialName("subject_id")
    val subjectId: Long,
    @SerialName("subject_name")
    val subjectName: String,
    @SerialName("year_mark")
    val yearMark: String? = null
) {
    fun toDomain() = SubjectMarksModel(
        subjectName = subjectName,
        periods = periods.map { period ->
            com.vobbla16.mesh.domain.model.subjectMarks.Period(
                title = period.title,
                average = period.value,
                startDate = period.startIso.toLocalDate(),
                endDate = period.endIso.toLocalDate(),
                fixedMark = period.fixedValue?.toInt(),
                marks = period.marks.map { mark ->
                    Mark(
                        id = mark.id,
                        isPoint = mark.isPoint,
                        comment = mark.comment,
                        controlForm = mark.controlFormName,
                        weight = mark.weight,
                        isExam = mark.isExam,
                        pointDate = mark.pointDate?.toLocalDate(),
                        value = mark.value.toInt()
                    )
                }
            )
        }
    )
}