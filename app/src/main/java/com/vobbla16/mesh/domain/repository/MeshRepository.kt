package com.vobbla16.mesh.domain.repository

import com.vobbla16.mesh.common.structures.OrLoading
import com.vobbla16.mesh.common.structures.Resource
import com.vobbla16.mesh.domain.model.acadYears.AcademicYearItemModel
import com.vobbla16.mesh.domain.model.classmates.ClassmateModel
import com.vobbla16.mesh.domain.model.homework.HomeworkItemsForDateModel
import com.vobbla16.mesh.domain.model.homeworkDone.HomeworkDoneModel
import com.vobbla16.mesh.domain.model.lessonInfo.LessonInfoModel
import com.vobbla16.mesh.domain.model.marks.MarksSubjectModel
import com.vobbla16.mesh.domain.model.profile.ProfileModel
import com.vobbla16.mesh.domain.model.ratingClass.anon.PersonRatingModel
import com.vobbla16.mesh.domain.model.schedule.LessonType
import com.vobbla16.mesh.domain.model.schedule.ScheduleModel
import com.vobbla16.mesh.domain.model.shortSchedule.ShortScheduleModel
import com.vobbla16.mesh.domain.model.subjectMarks.SubjectMarksModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import java.util.UUID

interface MeshRepository {
    suspend fun getProfile(): Flow<OrLoading<Resource<ProfileModel>>>

    suspend fun getSchedule(
        studentId: Long,
        date: LocalDate
    ): Flow<OrLoading<Resource<ScheduleModel>>>

    suspend fun getAcademicYears(): Flow<OrLoading<Resource<List<AcademicYearItemModel>>>>

    suspend fun getMarksReport(
        studentId: Long,
        academicYear: AcademicYearItemModel
    ): Flow<OrLoading<Resource<List<MarksSubjectModel>>>>

    suspend fun getHomework(
        studentId: Long,
        week: LocalDate
    ): Flow<OrLoading<Resource<List<HomeworkItemsForDateModel>>>>

    suspend fun getLessonInfo(
        studentId: Long,
        lessonId: Long,
        educationType: LessonType
    ): Flow<OrLoading<Resource<LessonInfoModel>>>

    suspend fun getClassmates(
        classUnitId: Long
    ): Flow<OrLoading<Resource<List<ClassmateModel>>>>

    suspend fun getClassRating(
        personId: UUID,
        date: LocalDate,
        subjectId: Long? = null
    ): Flow<OrLoading<Resource<List<PersonRatingModel>>>>

    suspend fun getShortSchedule(
        studentId: Long,
        dates: List<LocalDate>
    ): Flow<OrLoading<Resource<ShortScheduleModel>>>

    suspend fun getScheduleItemIdFromMark(
        markId: Long
    ): Flow<OrLoading<Resource<Long>>>

    suspend fun markHomeworkDone(
        homeworkId: Long
    ): Flow<OrLoading<Resource<HomeworkDoneModel>>>

    suspend fun unmarkHomeworkDone(
        homeworkId: Long
    ): Flow<OrLoading<Resource<HomeworkDoneModel>>>

    suspend fun getMarksForSubject(
        studentId: Long,
        subjectId: Long
    ): Flow<OrLoading<Resource<SubjectMarksModel>>>
}