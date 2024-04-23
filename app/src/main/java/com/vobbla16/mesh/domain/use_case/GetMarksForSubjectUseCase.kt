package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.domain.model.subjectMarks.SubjectMarksModel
import com.vobbla16.mesh.domain.repository.MeshRepository

class GetMarksForSubjectUseCase(
    private val meshRepository: MeshRepository,
    private val getStudentIdUseCase: GetStudentIdUseCase<SubjectMarksModel> = GetStudentIdUseCase(
        meshRepository
    )
) {
    suspend operator fun invoke(subjectId: Long) = getStudentIdUseCase { studentId ->
        meshRepository.getMarksForSubject(studentId, subjectId)
    }
}