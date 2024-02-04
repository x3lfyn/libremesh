package com.vobbla16.mesh.ui.screens.marksScreen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vobbla16.mesh.domain.model.classmates.ClassmateModel
import com.vobbla16.mesh.domain.model.ratingClass.anon.PersonRatingModel
import com.vobbla16.mesh.domain.model.ratingClass.anon.RankStatus
import com.vobbla16.mesh.ui.screens.marksScreen.ClassRatingItem
import java.util.UUID

@Composable
fun ClassRatingItemCard(
    item: ClassRatingItem,
    modifier: Modifier = Modifier,
    anonymous: Boolean = true
) {
    @Composable
    fun RankIndicator(rank: RankStatus) = Row {
        Text(
            text = "${rank.place} место",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(4.dp, 0.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = rank.averageMark.toString(),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(4.dp, 0.dp)
        )
    }

    Card(modifier) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (!anonymous) {
                Text(
                    text = "${item.second.lastName} ${item.second.firstName}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row(horizontalArrangement = Arrangement.SpaceAround) {
                RankIndicator(rank = item.first.currentRank)
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "arrow left",
                    modifier = Modifier.padding(6.dp, 0.dp)
                )
                RankIndicator(rank = item.first.previousRank)
            }
        }
    }
}


val sample = Pair(
    PersonRatingModel(
        UUID.randomUUID(),
        currentRank = RankStatus(4.8f, 2),
        previousRank = RankStatus(4.7f, 2),
        rankStatus = "stable",
        trend = "stable"
    ),
    ClassmateModel(
        UUID.randomUUID(),
        "Ivanov",
        "Ivan",
        "Ivanovich"
    )
)

@Preview
@Composable
fun ClassRatingItemCardPreview1() {
    ClassRatingItemCard(item = sample, Modifier.fillMaxWidth(), true)
}

@Preview
@Composable
fun ClassRatingItemCardPreview2() {
    ClassRatingItemCard(item = sample, Modifier.fillMaxWidth(), false)
}