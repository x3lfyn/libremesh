package com.vobbla16.mesh.ui.screens.marksScreen.subscreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.vobbla16.mesh.ui.commonComponents.genericHolderContainer.GenericHolderContainer
import com.vobbla16.mesh.ui.screens.marksScreen.MarksScreenViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowRating(vm: MarksScreenViewModel) {
    val state = vm.viewState.value

    GenericHolderContainer(holder = state.classmates, onRefresh = { /*TODO*/ }, onRetry = { /*TODO*/ }) {classmates ->
        LazyColumn {
            items(classmates) {
                Text(text = it.toString())
            }
        }
    }
}