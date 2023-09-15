package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.domain.model.homework.HomeworkItemsForDateModel
import com.vobbla16.mesh.domain.repository.MeshRepository
import kotlinx.datetime.LocalDate

class GetHomeworkUseCase(
    private val meshRepository: MeshRepository,
    private val getStudentId: GetStudentIdUseCase<List<HomeworkItemsForDateModel>> = GetStudentIdUseCase(
        meshRepository
    )
) {
    suspend operator fun invoke(week: LocalDate) = getStudentId { studentId ->
        meshRepository.getHomework(studentId, week)
    }
}