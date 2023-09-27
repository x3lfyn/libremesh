package com.vobbla16.mesh.ui.screens.marksScreen.subscreens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vobbla16.mesh.ui.screens.marksScreen.MarksScreenViewModel
import com.vobbla16.mesh.ui.screens.marksScreen.components.SubjectCard

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowBySubject(vm: MarksScreenViewModel) {
    val state = vm.viewState.value

    state.data?.let { subjects ->
        LazyColumn {
            itemsIndexed(subjects) { index, subj ->
                SubjectCard(
                    subject = subj,
                    opened = index in state.otherState.openedSubjectsIndices,
                    onClick = {
                        vm.toggleSubject(index)
                    },
                    modifier = Modifier.padding(4.dp, 2.dp)
                )
            }
        }
    }
}