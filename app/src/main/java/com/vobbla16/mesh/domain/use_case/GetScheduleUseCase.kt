package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.domain.model.schedule.ScheduleModel
import com.vobbla16.mesh.domain.repository.MeshRepository
import kotlinx.datetime.LocalDate

class GetScheduleUseCase(
    private val meshRepository: MeshRepository,
    private val getStudentId: GetStudentIdUseCase<ScheduleModel> = GetStudentIdUseCase(
        meshRepository
    )
) {
    suspend operator fun invoke(date: LocalDate) = getStudentId { studentId ->
        meshRepository.getSchedule(studentId, date)
    }
}