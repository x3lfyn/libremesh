package com.vobbla16.mesh.data.repository

import com.vobbla16.mesh.common.toStr
import com.vobbla16.mesh.data.remote.MeshApi
import com.vobbla16.mesh.domain.model.acadYears.AcademicYearItemModel
import com.vobbla16.mesh.domain.repository.MeshRepository
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

class MeshRepositoryImpl : MeshRepository {
    companion object {
        private val meshApi = MeshApi()
    }

    override suspend fun getProfile(token: String) = meshApi.getProfile(token)

    override suspend fun getSchedule(token: String, studentId: Int, date: LocalDate) =
        meshApi.getSchedule(token, studentId.toString(), date.toString())

    override suspend fun getAcademicYears(token: String) =
        meshApi.getAcademicYears(token)

    override suspend fun getMarksReport(
        token: String,
        studentId: Int,
        academicYear: AcademicYearItemModel
    ) = meshApi.getMarksReport(token, studentId.toString(), academicYear.id.toString())

    override suspend fun getHomework(
        token: String,
        studentId: Int,
        week: LocalDate
    ) = meshApi.getHomework(
        token,
        studentId,
        week.toStr(),
        (week.plus(DatePeriod(days = 7))).toStr()
    )
}
