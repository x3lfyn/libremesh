package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.domain.model.common.LessonSelector
import com.vobbla16.mesh.domain.model.lessonInfo.LessonInfoModel
import com.vobbla16.mesh.domain.repository.MeshRepository

class GetLessonInfoUseCase(
    private val meshRepository: MeshRepository,
    private val getStudentId: GetStudentIdUseCase<LessonInfoModel> = GetStudentIdUseCase(
        meshRepository
    )
) {
    suspend operator fun invoke(lessonSelector: LessonSelector) = getStudentId { studentId ->
        meshRepository.getLessonInfo(
            studentId,
            lessonSelector.scheduleItemId,
            lessonSelector.lessonType
        )
    }
}