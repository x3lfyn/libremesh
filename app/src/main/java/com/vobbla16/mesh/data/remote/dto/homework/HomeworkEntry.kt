package com.vobbla16.mesh.data.remote.dto.homework


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeworkEntry(
    @SerialName("atomic_objects")
    val atomicObjects: String?,
    @SerialName("attachment_ids")
    val attachmentIds: List<Int>,
    @SerialName("attachments")
    val attachments: List<Attachment>,
    @SerialName("books")
    val books: String?,
    @SerialName("controllable_item_ids")
    val controllableItemIds: String?,
    @SerialName("controllable_items")
    val controllableItems: List<String>,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("data")
    val `data`: String?,
    @SerialName("deleted_at")
    val deletedAt: String?,
    @SerialName("description")
    val description: String,
    @SerialName("duration")
    val duration: Int,
    @SerialName("eom_urls")
    val eomUrls: List<String>,
    @SerialName("game_apps")
    val gameApps: String?,
    @SerialName("homework")
    val homework: Homework,
    @SerialName("homework_entry_comments")
    val homeworkEntryComments: List<String>,
    @SerialName("homework_entry_student_answer")
    val homeworkEntryStudentAnswer: StudentAnswer?,
    @SerialName("homework_id")
    val homeworkId: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("is_digital_homework")
    val isDigitalHomework: Boolean?,
    @SerialName("long_term")
    val longTerm: Boolean,
    @SerialName("no_duration")
    val noDuration: Boolean?,
    @SerialName("related_materials")
    val relatedMaterials: String?,
    @SerialName("scripts")
    val scripts: String?,
    @SerialName("student_ids")
    val studentIds: List<Int>,
    @SerialName("tests")
    val tests: String?,
    @SerialName("update_comment")
    val updateComment: String?,
    @SerialName("updated_at")
    val updatedAt: String
)