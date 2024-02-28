package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.domain.model.shortSchedule.ShortScheduleModel
import com.vobbla16.mesh.domain.repository.MeshRepository
import kotlinx.datetime.LocalDate

class GetShortScheduleUseCase(
    private val meshRepository: MeshRepository,
    private val getStudentId: GetStudentIdUseCase<ShortScheduleModel> = GetStudentIdUseCase(
        meshRepository
    )
) {
    suspend operator fun invoke(dates: List<LocalDate>) = getStudentId { studentId ->
        meshRepository.getShortSchedule(studentId, dates)
    }
}