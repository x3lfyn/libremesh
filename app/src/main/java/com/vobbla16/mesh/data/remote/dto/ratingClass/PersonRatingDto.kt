package com.vobbla16.mesh.data.remote.dto.ratingClass


import com.vobbla16.mesh.domain.model.ratingClass.anon.PersonRatingModel
import com.vobbla16.mesh.domain.model.ratingClass.anon.RankStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class PersonRatingDto(
    @SerialName("imageId")
    val imageId: Int,
    @SerialName("personId")
    val personId: String,
    @SerialName("previousRank")
    val previousRank: PreviousRank,
    @SerialName("rank")
    val rank: Rank
) {
    fun toDomain() = PersonRatingModel(
        personId = UUID.fromString(personId),
        trend = rank.trend,
        rankStatus = rank.rankStatus,
        currentRank = RankStatus(
            averageMark = rank.averageMarkFive.toFloat(),
            place = rank.rankPlace
        ),
        previousRank = RankStatus(
            averageMark = previousRank.averageMarkFive.toFloat(),
            place = previousRank.rankPlace
        )
    )
}