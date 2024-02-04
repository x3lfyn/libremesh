package com.vobbla16.mesh.ui.screens.marksScreen.subscreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vobbla16.mesh.ui.commonComponents.genericHolderContainer.GenericHolderContainer
import com.vobbla16.mesh.ui.screens.marksScreen.MarksScreenViewModel
import com.vobbla16.mesh.ui.screens.marksScreen.components.ClassRatingItemCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowRating(vm: MarksScreenViewModel) {
    val state = vm.viewState.value

    var anonymuous by remember {
        mutableStateOf(false)
    }

    GenericHolderContainer(
        holder = state.ratingClass,
        onRefresh = { vm.getRatingClass(true) },
        onRetry = { vm.getRatingClass(false) }
    ) { rating ->
        LazyColumn {
            item {
             Checkbox(checked = anonymuous, onCheckedChange = { anonymuous = !anonymuous })
            }
            items(rating) {
                ClassRatingItemCard(item = it, modifier = Modifier.padding(6.dp), anonymous = anonymuous)
            }
        }
    }
}