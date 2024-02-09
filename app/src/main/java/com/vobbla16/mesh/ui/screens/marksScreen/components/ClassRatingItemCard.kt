package com.vobbla16.mesh.ui.screens.marksScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
    fun RankIndicator(rank: RankStatus) {
        Text(
            text = "${rank.place} место",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(4.dp, 0.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        MarkDefault(
            mark = MarkDefaultValue(rank.averageMark, null, false),
            size = MarkDefaultSize.Small
        )
    }

    Card(modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                RankIndicator(rank = item.first.currentRank)
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "arrow left",
                    modifier = Modifier.padding(6.dp, 0.dp)
                )
                RankIndicator(rank = item.first.previousRank)
            }
            if (!anonymous) {
                Text(
                    text = "${item.second.lastName} ${item.second.firstName}",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 4.dp)
                )
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