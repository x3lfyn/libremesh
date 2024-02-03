package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.common.mapIfOk
import com.vobbla16.mesh.common.mergeIfOk

class GetRatingClassDeanonUseCase(
    private val getClassmatesUseCase: GetClassmatesUseCase,
    private val getRatingClassUseCase: GetRatingClassUseCase
) {
    suspend operator fun invoke() = mergeIfOk(getClassmatesUseCase()) { classmates ->
        val classmatesById = classmates.associateBy { it.personId }

        getRatingClassUseCase().mapIfOk { rating ->
            rating.associateBy { it.personId }.entries.associateBy({it.value}) {it.key}.map {
                it.key to classmatesById[it.value]!!
            }.sortedBy { it.first.currentRank.place }
        }
    }
}