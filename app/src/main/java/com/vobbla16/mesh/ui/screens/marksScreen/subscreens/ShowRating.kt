package com.vobbla16.mesh.ui.screens.marksScreen.subscreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vobbla16.mesh.ui.commonComponents.genericHolderContainer.GenericHolderContainer
import com.vobbla16.mesh.ui.screens.marksScreen.MarksScreenViewModel
import com.vobbla16.mesh.ui.screens.marksScreen.components.ClassRatingItemCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowRating(vm: MarksScreenViewModel) {
    val state = vm.viewState.value

    GenericHolderContainer(
        holder = state.ratingClass,
        onRefresh = { vm.getRatingClass(true) },
        onRetry = { vm.getRatingClass(false) }
    ) { rating ->
        LazyColumn {
            item {
                Text(text = "Рейтинг класса", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(6.dp))
            }
            items(rating) {
                ClassRatingItemCard(
                    item = it,
                    modifier = Modifier.padding(6.dp),
                    anonymous = state.anonymousRating
                )
            }
        }
    }
}