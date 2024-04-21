package com.vobbla16.mesh.ui.screens.marksScreen.subscreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vobbla16.mesh.R
import com.vobbla16.mesh.ui.screens.marksScreen.MarksScreenViewModel
import com.vobbla16.mesh.ui.screens.marksScreen.components.SingleDayCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowByDate(vm: MarksScreenViewModel) {
    val state = vm.viewState.value

    val scope = rememberCoroutineScope()

    state.dataGroupedByDate?.let { data ->
        if (data.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.no_marks),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        } else {
            LazyColumn {
                items(data) { day ->
                    SingleDayCard(report = day, modifier = Modifier.padding(6.dp), onClick = {
                        scope.launch {
                            vm.openMarkInfo(it)
                        }
                    })
                }
            }
        }
    }
}