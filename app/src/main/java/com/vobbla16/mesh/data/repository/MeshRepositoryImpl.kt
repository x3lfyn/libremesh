package com.vobbla16.mesh.data.repository

import com.vobbla16.mesh.common.structures.OrLoading
import com.vobbla16.mesh.common.structures.Resource
import com.vobbla16.mesh.common.toStr
import com.vobbla16.mesh.data.remote.MeshApi
import com.vobbla16.mesh.domain.model.acadYears.AcademicYearItemModel
import com.vobbla16.mesh.domain.model.classmates.ClassmateModel
import com.vobbla16.mesh.domain.model.homeworkDone.HomeworkDoneModel
import com.vobbla16.mesh.domain.model.lessonInfo.LessonInfoModel
import com.vobbla16.mesh.domain.model.ratingClass.anon.PersonRatingModel
import com.vobbla16.mesh.domain.model.schedule.LessonType
import com.vobbla16.mesh.domain.model.shortSchedule.ShortScheduleModel
import com.vobbla16.mesh.domain.model.subjectMarks.SubjectMarksModel
import com.vobbla16.mesh.domain.repository.MeshRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import java.util.UUID

class MeshRepositoryImpl : MeshRepository {
    companion object {
        private val meshApi = MeshApi()
    }

    override suspend fun getProfile() = meshApi.getProfile()

    override suspend fun getSchedule(studentId: Long, date: LocalDate) =
        meshApi.getSchedule(studentId.toString(), date.toString())

    override suspend fun getAcademicYears() =
        meshApi.getAcademicYears()

    override suspend fun getMarksReport(
        studentId: Long,
        academicYear: AcademicYearItemModel
    ) = meshApi.getMarksReport(studentId.toString(), academicYear.id.toString())

    override suspend fun getHomework(
        studentId: Long,
        week: LocalDate
    ) = meshApi.getHomework(
        studentId,
        week.toStr(),
        (week.plus(DatePeriod(days = 7))).toStr()
    )

    override suspend fun getLessonInfo(
        studentId: Long,
        lessonId: Long,
        educationType: LessonType
    ): Flow<OrLoading<Resource<LessonInfoModel>>> = meshApi.getLessonInfo(
        studentId,
        lessonId,
        educationType.str
    )

    override suspend fun getClassmates(classUnitId: Long)
            : Flow<OrLoading<Resource<List<ClassmateModel>>>> = meshApi.getClassmates(
        classUnitId
    )

    override suspend fun getClassRating(
        personId: UUID,
        date: LocalDate,
        subjectId: Long?
    ): Flow<OrLoading<Resource<List<PersonRatingModel>>>> = meshApi.getClassRating(
        personId, date, subjectId
    )

    override suspend fun getShortSchedule(
        studentId: Long,
        dates: List<LocalDate>
    ): Flow<OrLoading<Resource<ShortScheduleModel>>> = meshApi.getShortSchedule(
        studentId, dates
    )

    override suspend fun getScheduleItemIdFromMark(markId: Long): Flow<OrLoading<Resource<Long>>> =
        meshApi.getMarkInfo(markId)

    override suspend fun markHomeworkDone(homeworkId: Long): Flow<OrLoading<Resource<HomeworkDoneModel>>> =
        meshApi.markHomeworkDone(homeworkId)

    override suspend fun unmarkHomeworkDone(homeworkId: Long): Flow<OrLoading<Resource<HomeworkDoneModel>>> =
        meshApi.unmarkHomeworkDone(homeworkId)

    override suspend fun getMarksForSubject(
        studentId: Long,
        subjectId: Long
    ): Flow<OrLoading<Resource<SubjectMarksModel>>> = meshApi.marksForSubject(studentId, subjectId)
}
