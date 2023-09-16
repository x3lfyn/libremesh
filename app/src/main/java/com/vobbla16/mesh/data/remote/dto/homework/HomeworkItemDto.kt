package com.vobbla16.mesh.data.remote.dto.homework


import com.vobbla16.mesh.common.fromMeshStr2LocalDT
import com.vobbla16.mesh.common.fromMeshStr2LocalDate
import com.vobbla16.mesh.domain.model.homework.HomeworkItem
import com.vobbla16.mesh.domain.model.homework.HomeworkItemsForDateModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeworkItemDto(
    @SerialName("attachment_ids")
    val attachmentIds: List<Int>,
    @SerialName("attachments")
    val attachments: List<String>,
    @SerialName("comment")
    val comment: String?,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("deleted_at")
    val deletedAt: String?,
    @SerialName("homework_entry")
    val homeworkEntry: HomeworkEntry,
    @SerialName("homework_entry_id")
    val homeworkEntryId: Int,
    @SerialName("id")
    val id: Long,
    @SerialName("is_ready")
    val isReady: Boolean,
    @SerialName("last_student_action_at")
    val lastStudentActionAt: String?,
    @SerialName("remote_attachments")
    val remoteAttachments: List<String>,
    @SerialName("student_id")
    val studentId: Int,
    @SerialName("student_name")
    val studentName: String,
    @SerialName("updated_at")
    val updatedAt: String
)

fun HomeworkItemDto.toDomain() = HomeworkItem(
    isReady = this.isReady,
    createdAt = fromMeshStr2LocalDT(this.createdAt),
    updatedAt = fromMeshStr2LocalDT(this.updatedAt),
    description = this.homeworkEntry.description,
    subjectId = this.homeworkEntry.homework.subjectId,
    subjectName = this.homeworkEntry.homework.subject.name,
    teacherId = this.homeworkEntry.homework.teacherId,
    dateAssignedOn = fromMeshStr2LocalDate(this.homeworkEntry.homework.dateAssignedOn),
    datePreparedFor = fromMeshStr2LocalDate(this.homeworkEntry.homework.datePreparedFor)
)

fun List<HomeworkItemDto>.toDomain(): List<HomeworkItemsForDateModel> =
    this.map { it.toDomain() }
        .groupBy { it.datePreparedFor }
        .map { HomeworkItemsForDateModel(it.key, it.value) }
        .sortedBy { it.date }