package com.vobbla16.mesh.ui.screens.marksScreen.subscreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vobbla16.mesh.ui.screens.destinations.SubjectScreenDestination
import com.vobbla16.mesh.ui.screens.marksScreen.MarksScreenViewModel
import com.vobbla16.mesh.ui.screens.marksScreen.components.SubjectCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowBySubject(vm: MarksScreenViewModel, navigator: DestinationsNavigator) {
    val state = vm.viewState.value

    state.marksData.data?.let { subjects ->
        LazyColumn {
            itemsIndexed(subjects) { index, subj ->
                SubjectCard(
                    subject = subj,
                    onClick = {
                        navigator.navigate(SubjectScreenDestination(subjects[index].subjectId))
                    },
                    modifier = Modifier.padding(4.dp, 2.dp)
                )
            }
        }
    }
}