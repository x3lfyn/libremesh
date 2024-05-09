package com.vobbla16.mesh.data.remote.dto.lessonInfo


import com.vobbla16.mesh.common.orDefault
import com.vobbla16.mesh.common.secsToLocalDateTime
import com.vobbla16.mesh.data.remote.dto.schedule.Mark
import com.vobbla16.mesh.domain.model.lessonInfo.Homework
import com.vobbla16.mesh.domain.model.lessonInfo.LessonInfoModel
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class LessonInfoDto(
    @SerialName("begin_time")
    val beginTime: String,
    @SerialName("begin_utc")
    val beginUtc: Long,
    @SerialName("building_name")
    val buildingName: String? = null,
    @SerialName("date")
    val date: String,
    @SerialName("end_time")
    val endTime: String,
    @SerialName("end_utc")
    val endUtc: Long,
    @SerialName("homework_presence_status_id")
    val homeworkPresenceStatusId: Long? = null,
    @SerialName("id")
    val id: Long,
    @SerialName("is_missed_lesson")
    val isMissedLesson: Boolean,
    @SerialName("is_virtual")
    val isVirtual: Boolean,
    @SerialName("lesson_education_type")
    val lessonEducationType: String,
    @SerialName("lesson_homeworks")
    val lessonHomeworks: List<LessonHomework>,
    @SerialName("lesson_type")
    val lessonType: String,
    @SerialName("marks")
    val marks: List<Mark>,
    @SerialName("plan_id")
    val planId: Long? = null,
    @SerialName("room_name")
    val roomName: String? = null,
    @SerialName("room_number")
    val roomNumber: String? = null,
    @SerialName("subject_id")
    val subjectId: Long,
    @SerialName("subject_name")
    val subjectName: String,
    @SerialName("teacher")
    val teacher: Teacher?
)

fun LessonInfoDto.toDomain() = LessonInfoModel(
    id = id,
    beginTime =  secsToLocalDateTime(beginUtc),
    endTime = secsToLocalDateTime(endUtc),
    subjectId = subjectId,
    teacher = teacher?.let { "${it.lastName} ${it.firstName} ${it.middleName}" },
    homeworks = lessonHomeworks.map { homework ->
        Homework(
            id = homework.homeworkEntryStudentId,
            name = homework.homework,
            isDone = homework.isDone,
            additionMaterials = homework.additionalMaterials.map {material ->
                when(material) {
                    is AdditionalMaterial.Attachment -> com.vobbla16.mesh.domain.model.lessonInfo.AdditionalMaterial.Attachment(
                        title = material.title,
                        links = material.links.map { link -> link.url }
                    )
                    is AdditionalMaterial.TestTaskBinding -> com.vobbla16.mesh.domain.model.lessonInfo.AdditionalMaterial.TestTaskBinding(
                        title = material.title,
                        uuid = material.uuid?.let { UUID.fromString(it) }
                    )
                    is AdditionalMaterial.GameApp -> com.vobbla16.mesh.domain.model.lessonInfo.AdditionalMaterial.GameApp(
                        a = 0
                    )
                    is AdditionalMaterial.AtomicObject -> com.vobbla16.mesh.domain.model.lessonInfo.AdditionalMaterial.AtomicObject(
                        a = 0
                    )
                    is AdditionalMaterial.LessonTemplate -> com.vobbla16.mesh.domain.model.lessonInfo.AdditionalMaterial.LessonTemplate(
                        a = 0
                    )
                    is AdditionalMaterial.TestSpecBinding -> com.vobbla16.mesh.domain.model.lessonInfo.AdditionalMaterial.TestSpecBinding(
                        a = 0
                    )
                    is AdditionalMaterial.Workbook -> com.vobbla16.mesh.domain.model.lessonInfo.AdditionalMaterial.Workbook(
                        a = 0
                    )
                    is AdditionalMaterial.FizikonModule -> com.vobbla16.mesh.domain.model.lessonInfo.AdditionalMaterial.FizikonModule(
                        a = 0
                    )
                }
            }
        )
    },
    room = "${buildingName.orDefault()}, ${roomName.orDefault()}, ${roomNumber.orDefault()}",
    subjectName = subjectName,
    marks = marks.map { mark ->
        com.vobbla16.mesh.domain.model.lessonInfo.Mark(
            id = mark.id,
            value = mark.value.toInt(),
            weight = mark.weight,
            comment = if (mark.commentExists) mark.comment else null,
            controlForm = mark.controlFormName,
            isPoint = mark.isPoint,
            pointDate = mark.pointDate?.toLocalDate(),
            isExam = mark.isExam,
            createdAt = mark.createdAt.toLocalDateTime(),
            updatedAt = mark.updatedAt.toLocalDateTime()
        )
    },
    isMissed = isMissedLesson,
)