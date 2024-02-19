package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.common.mapIfOk
import com.vobbla16.mesh.common.mergeIfOk

class GetRatingClassDeanonUseCase(
    private val getClassmatesUseCase: GetClassmatesUseCase,
    private val getRatingClassUseCase: GetRatingClassUseCase,
    private val getStudentUseCase: GetStudentUseCase
) {
    suspend operator fun invoke() = mergeIfOk(getClassmatesUseCase()) { classmates ->
        val classmatesById = classmates.associateBy { it.personId }

        mergeIfOk(getStudentUseCase()) { currentStudent ->
            getRatingClassUseCase().mapIfOk { rating ->
                rating.associateBy { it.personId }.entries.associateBy({ it.value }) { it.key }
                    .map {
                        Triple(
                            it.key,
                            classmatesById[it.value]!!,
                            currentStudent.children[0].contingentGuid == it.value
                        )
                    }.sortedBy { it.first.currentRank.place }
            }
        }
    }
}