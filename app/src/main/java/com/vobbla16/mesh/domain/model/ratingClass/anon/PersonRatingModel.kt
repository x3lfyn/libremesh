package com.vobbla16.mesh.domain.model.ratingClass.anon

import java.util.UUID

data class PersonRatingModel(
    val personId: UUID,
    val currentRank: RankStatus,
    val previousRank: RankStatus,
    val rankStatus: String,
    val trend: String
)
