package com.vobbla16.mesh.ui.screens.marksScreen.subscreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vobbla16.mesh.ui.screens.marksScreen.MarksScreenViewModel
import com.vobbla16.mesh.ui.screens.marksScreen.components.SubjectCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowBySubject(vm: MarksScreenViewModel) {
    val state = vm.viewState.value

    val scope = rememberCoroutineScope()

    state.marksData.data?.let { subjects ->
        LazyColumn {
            itemsIndexed(subjects) { index, subj ->
                SubjectCard(
                    subject = subj,
                    opened = index in state.openedSubjectsIndices,
                    onClick = {
                        vm.toggleSubject(index)
                    },
                    modifier = Modifier.padding(4.dp, 2.dp),
                    onClickMark = {
                        scope.launch {
                            vm.openMarkInfo(it)
                        }
                    }
                )
            }
        }
    }
}