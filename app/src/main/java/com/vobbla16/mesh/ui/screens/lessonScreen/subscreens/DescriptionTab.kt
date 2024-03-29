package com.vobbla16.mesh.ui.screens.lessonScreen.subscreens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.vobbla16.mesh.R
import com.vobbla16.mesh.domain.model.schedule.LessonType
import com.vobbla16.mesh.ui.commonComponents.genericHolderContainer.GenericHolderContainer
import com.vobbla16.mesh.ui.screens.lessonScreen.LessonScreenViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DescriptionTab(vm: LessonScreenViewModel) {
    val state = vm.viewState.value

    GenericHolderContainer(
        holder = state.lessonInfo,
        onRefresh = { vm.refresh() },
        onRetry = { vm.retry() }
    ) { model ->
        Column(
            Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            model.room?.let { room ->
                Text(text = room, style = MaterialTheme.typography.titleMedium)
            }

            model.teacher?.let { teacher ->
                Text(text = teacher, style = MaterialTheme.typography.labelLarge)
            }

            when (state.selectedLesson?.lessonType) {
                LessonType.AE -> {
                    Text(
                        text = stringResource(id = R.string.lesson_type_ae),
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                LessonType.EC -> {
                    Text(
                        text = stringResource(id = R.string.lesson_type_ec),
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                else -> {}
            }

            if (model.isMissed) {
                Text(
                    text = stringResource(R.string.lesson_screen_skipped),
                    style = MaterialTheme.typography.labelLarge,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}