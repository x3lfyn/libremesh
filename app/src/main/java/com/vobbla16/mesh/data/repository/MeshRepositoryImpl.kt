package com.vobbla16.mesh.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vobbla16.mesh.data.remote.MeshApi
import com.vobbla16.mesh.data.remote.dto.acadYears.toDomain
import com.vobbla16.mesh.data.remote.dto.marks.toDomain
import com.vobbla16.mesh.data.remote.dto.profile.toDomain
import com.vobbla16.mesh.data.remote.dto.schedule.toDomain
import com.vobbla16.mesh.domain.model.acadYears.AcademicYear
import com.vobbla16.mesh.domain.model.homework.HomeworkItemsForDate
import com.vobbla16.mesh.domain.model.marks.MarksSubject
import com.vobbla16.mesh.domain.repository.MeshRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

class MeshRepositoryImpl : MeshRepository {
    companion object {
        private val meshApi = MeshApi()
    }

    override suspend fun getProfile(token: String) = meshApi.getProfile(token).toDomain()

    override suspend fun getSchedule(token: String, studentId: Int, date: LocalDate) =
        meshApi.getSchedule(token, studentId.toString(), date.toString()).toDomain()

    override suspend fun getAcademicYears(): List<AcademicYear> =
        meshApi.getAcademicYears().toDomain()

    override suspend fun getMarksReport(
        token: String,
        studentId: Int,
        academicYear: AcademicYear
    ): List<MarksSubject> =
        meshApi.getMarksReport(token, studentId.toString(), academicYear.id.toString()).toDomain()

    override suspend fun getHomework(
        token: String,
        studentId: Int
    ): Flow<PagingData<HomeworkItemsForDate>> = Pager(
        config = PagingConfig(pageSize = 7),
        pagingSourceFactory = { HomeworkPagingSource(meshApi, token, studentId) }
    ).flow
}
