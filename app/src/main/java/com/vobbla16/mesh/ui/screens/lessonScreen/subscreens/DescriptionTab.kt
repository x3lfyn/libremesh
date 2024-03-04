package com.vobbla16.mesh.ui.screens.lessonScreen.subscreens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.sp
import com.vobbla16.mesh.common.toHumanStr
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
        Column(Modifier.fillMaxWidth()) {
            Text(
                text = buildAnnotatedString {
                    append(model.beginTime.date.toHumanStr(LocalConfiguration.current))
                    append(", ")
                    append(model.beginTime.time.toString())
                    appendInlineContent("arrow")
                    append("${model.durationMinutes()} минyт")
                    appendInlineContent("arrow")
                    append(model.endTime.time.toString())
                },
                inlineContent = mapOf("arrow" to InlineTextContent(
                    Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.TextCenter)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "arrow icon"
                    )
                }),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            model.teacher?.let { teacher ->
                Text(text = teacher, style = MaterialTheme.typography.labelLarge)
            }
            model.room?.let { room ->
                Text(text = room, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}