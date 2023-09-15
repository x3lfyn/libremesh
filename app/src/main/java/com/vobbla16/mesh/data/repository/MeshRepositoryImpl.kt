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

    override suspend fun getProfile() = meshApi.getProfile()

    override suspend fun getSchedule(studentId: Int, date: LocalDate) =
        meshApi.getSchedule(studentId.toString(), date.toString())

    override suspend fun getAcademicYears() =
        meshApi.getAcademicYears()

    override suspend fun getMarksReport(
        studentId: Int,
        academicYear: AcademicYearItemModel
    ) = meshApi.getMarksReport(studentId.toString(), academicYear.id.toString())

    override suspend fun getHomework(
        studentId: Int,
        week: LocalDate
    ) = meshApi.getHomework(
        studentId,
        week.toStr(),
        (week.plus(DatePeriod(days = 7))).toStr()
    )
}
