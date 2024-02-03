package com.vobbla16.mesh.data.remote

import com.vobbla16.mesh.common.Constants
import com.vobbla16.mesh.common.toStr
import com.vobbla16.mesh.common.wrapToResourceOrLoading
import com.vobbla16.mesh.data.remote.dto.acadYears.AcademicYearsItemDto
import com.vobbla16.mesh.data.remote.dto.acadYears.toDomain
import com.vobbla16.mesh.data.remote.dto.classmates.ClassmateDto
import com.vobbla16.mesh.data.remote.dto.homework.HomeworkItemDto
import com.vobbla16.mesh.data.remote.dto.homework.toDomain
import com.vobbla16.mesh.data.remote.dto.lessonInfo.LessonInfoDto
import com.vobbla16.mesh.data.remote.dto.lessonInfo.toDomain
import com.vobbla16.mesh.data.remote.dto.marks.MarksReportItemDto
import com.vobbla16.mesh.data.remote.dto.marks.toDomain
import com.vobbla16.mesh.data.remote.dto.profile.ProfileDto
import com.vobbla16.mesh.data.remote.dto.profile.toDomain
import com.vobbla16.mesh.data.remote.dto.ratingClass.PersonRatingDto
import com.vobbla16.mesh.data.remote.dto.schedule.ScheduleDto
import com.vobbla16.mesh.data.remote.dto.schedule.toDomain
import com.vobbla16.mesh.domain.model.acadYears.AcademicYearItemModel
import com.vobbla16.mesh.domain.model.classmates.ClassmateModel
import com.vobbla16.mesh.domain.model.homework.HomeworkItemsForDateModel
import com.vobbla16.mesh.domain.model.lessonInfo.LessonInfoModel
import com.vobbla16.mesh.domain.model.marks.MarksSubjectModel
import com.vobbla16.mesh.domain.model.profile.ProfileModel
import com.vobbla16.mesh.domain.model.ratingClass.anon.PersonRatingModel
import com.vobbla16.mesh.domain.model.schedule.ScheduleModel
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import kotlinx.datetime.LocalDate
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class MeshApi : KoinComponent {
    private val httpClient: HttpClient by inject()
    suspend fun getSchedule(
        studentId: String,
        date: String
    ) = wrapToResourceOrLoading<ScheduleDto, ScheduleModel>({ it.toDomain() }) {
        httpClient.get(Constants.MESH_API_BASE_DOMAIN_SCHOOL + Constants.SCHEDULE_ENDPOINT) {
            url {
                parameter("student_id", studentId)
                parameter("date", date)
            }
        }
    }

    suspend fun getProfile() =
        wrapToResourceOrLoading<ProfileDto, ProfileModel>({ it.toDomain() }) {
            httpClient.get(Constants.MESH_API_BASE_DOMAIN_SCHOOL + Constants.PROFILE_ENDPOINT)
        }

    suspend fun getAcademicYears() =
        wrapToResourceOrLoading<List<AcademicYearsItemDto>, List<AcademicYearItemModel>>({ it.toDomain() }) {
            httpClient.get(Constants.MESH_API_BASE_DOMAIN_SCHOOL + Constants.ACADEMIC_YEARS_ENDPOINT)
        }

    suspend fun getMarksReport(
        studentId: String,
        academicYearId: String
    ) =
        wrapToResourceOrLoading<List<MarksReportItemDto>, List<MarksSubjectModel>>({ it.toDomain() }) {
            httpClient.get(Constants.MESH_API_BASE_DOMAIN_SCHOOL + Constants.MARKS_ENDPOINT) {
                url {
                    parameter("academic_year_id", academicYearId)
                    parameter("student_profile_id", studentId)
                }
            }
        }

    suspend fun getHomework(
        studentId: Long,
        beginDate: String,
        endDate: String
    ) =
        wrapToResourceOrLoading<List<HomeworkItemDto>, List<HomeworkItemsForDateModel>>({ it.toDomain() }) {
            httpClient.get(Constants.MESH_API_BASE_DOMAIN_DNEVNIK + "/core/api/student_homeworks") {
                url {
                    parameter("student_profile_id", studentId)
                    parameter("begin_prepared_date", beginDate)
                    parameter("end_prepared_date", endDate)
                }
            }
        }

    suspend fun getLessonInfo(studentId: Long, scheduleItemId: Long, type: String) =
        wrapToResourceOrLoading<LessonInfoDto, LessonInfoModel>({ it.toDomain() }) {
            httpClient.get(Constants.MESH_API_BASE_DOMAIN_SCHOOL + Constants.LESSON_INFO_ENDPOINT + scheduleItemId.toString()) {
                url {
                    parameter("student_id", studentId)
                    parameter("type", type)
                }
            }
        }

    suspend fun getClassmates(classUnitId: Long) =
        wrapToResourceOrLoading<List<ClassmateDto>, List<ClassmateModel>>({ it.map { it.toDomain() } }) {
            httpClient.get(Constants.MESH_API_BASE_DOMAIN_DNEVNIK + Constants.CLASSMATES_ENDPOINT) {
                url {
                    parameter("class_unit_id", classUnitId)
                    parameter("per_page", Int.MAX_VALUE)
                    parameter("types", "student")
                }
            }
        }

    suspend fun getRatingClass(
        personId: UUID,
        date: LocalDate
    ) = wrapToResourceOrLoading<List<PersonRatingDto>, List<PersonRatingModel>>({ it.map { it.toDomain() } }) {
        httpClient.get(Constants.MESH_API_BASE_DOMAIN_SCHOOL + Constants.RATING_CLASS_ENDPOINT) {
            url {
                parameter("personId", personId.toString())
                parameter("date", date.toString())
            }
        }
    }
}