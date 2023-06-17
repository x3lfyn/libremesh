package com.vobbla16.mesh.ui.screens.marksScreen.subscreens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vobbla16.mesh.ui.screens.marksScreen.MarksScreenViewModel
import com.vobbla16.mesh.ui.screens.marksScreen.components.SingleDayCard

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowByDate(vm: MarksScreenViewModel) {
    val state = vm.viewState.value

    state.dataGroupedByDate?.let { data ->
        LazyColumn {
            items(data) {day ->
                SingleDayCard(report = day, modifier = Modifier.padding(6.dp))
            }
        }
    }
}