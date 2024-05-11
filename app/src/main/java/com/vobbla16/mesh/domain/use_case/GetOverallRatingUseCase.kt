package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.common.localDateTimeNow
import com.vobbla16.mesh.common.mergeIfOk
import com.vobbla16.mesh.domain.repository.MeshRepository

class GetOverallRatingUseCase(
    private val meshRepository: MeshRepository,
    private val getStudentUseCase: GetStudentUseCase
) {
    suspend operator fun invoke() = mergeIfOk(getStudentUseCase()) { profile ->
        meshRepository.getClassRating(profile.children[0].contingentGuid, localDateTimeNow().date)
    }
}