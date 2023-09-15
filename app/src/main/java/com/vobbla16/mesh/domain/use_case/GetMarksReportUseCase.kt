package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.common.mergeIfOk
import com.vobbla16.mesh.domain.model.marks.MarksSubjectModel
import com.vobbla16.mesh.domain.repository.MeshRepository

class GetMarksReportUseCase(
    private val meshRepository: MeshRepository,
    private val getStudentId: GetStudentIdUseCase<List<MarksSubjectModel>> = GetStudentIdUseCase(
        meshRepository
    )
) {
    suspend operator fun invoke() = getStudentId { studentId ->
        mergeIfOk(meshRepository.getAcademicYears()) { years ->
            val currentYear = years.find { it.isCurrentYear }!!
            meshRepository.getMarksReport(studentId, currentYear)
        }
    }
}