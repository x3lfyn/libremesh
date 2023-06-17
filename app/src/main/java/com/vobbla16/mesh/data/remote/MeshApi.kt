package com.vobbla16.mesh.data.remote

import com.vobbla16.mesh.data.remote.dto.acadYears.AcademicYearsItem
import com.vobbla16.mesh.data.remote.dto.homework.HomeworkDtoItem
import com.vobbla16.mesh.data.remote.dto.marks.MarksReportItem
import com.vobbla16.mesh.data.remote.dto.profile.Profile
import com.vobbla16.mesh.data.remote.dto.schedule.Schedule
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MeshApi : KoinComponent {
    private val httpClient: HttpClient by inject()

    suspend fun getSchedule(token: String, studentId: String, date: String): Schedule {
        return httpClient.get("https://dnevnik.mos.ru/mobile/api/schedule") {
            url {
                parameter("student_id", studentId)
                parameter("date", date)
            }
            header("Auth-Token", token)
        }.body()
    }

    suspend fun getProfile(token: String): Profile {
        return httpClient.get("https://dnevnik.mos.ru/mobile/api/profile") {
            header("Auth-Token", token)
            header("Authorization", token)
        }.body()
    }

    suspend fun getAcademicYears(): List<AcademicYearsItem> {
        return httpClient.get("https://dnevnik.mos.ru/core/api/academic_years").body()
    }

    suspend fun getMarksReport(
        token: String,
        studentId: String,
        academicYearId: String
    ): List<MarksReportItem> {
        return httpClient.get("https://dnevnik.mos.ru/reports/api/progress/json") {
            url {
                parameter("academic_year_id", academicYearId)
                parameter("student_profile_id", studentId)
            }
            header("Auth-Token", token)
            header("Authorization", token)
        }.body()
    }

    suspend fun getHomework(
        token: String,
        studentId: Int,
        beginDate: String,
        endDate: String
    ): List<HomeworkDtoItem> {
        return httpClient.get("https://dnevnik.mos.ru/core/api/student_homeworks") {
            url {
                parameter("student_profile_id", studentId)
                parameter("begin_prepared_date", beginDate)
                parameter("end_prepared_date", endDate)
            }
            header("Auth-Token", token)
            header("Authorization", token)
        }.body()
    }
}