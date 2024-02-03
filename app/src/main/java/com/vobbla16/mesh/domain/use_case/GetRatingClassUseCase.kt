package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.common.localDateTimeNow
import com.vobbla16.mesh.common.mergeIfOk
import com.vobbla16.mesh.domain.repository.MeshRepository
import kotlinx.coroutines.flow.map

class GetRatingClassUseCase(
    private val meshRepository: MeshRepository,
    private val getStudentUseCase: GetStudentUseCase
) {
    suspend operator fun invoke() = mergeIfOk(getStudentUseCase()) { profile ->
        meshRepository.getRatingClass(profile.children[0].contingentGuid, localDateTimeNow().date)
    }
}