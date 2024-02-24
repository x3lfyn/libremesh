package com.vobbla16.mesh.ui.screens.lessonScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable

enum class Tabs(
    val title: @Composable () -> Unit,
    val icon: @Composable () -> Unit,
    val content: @Composable (LessonScreenViewModel) -> Unit
) {
    Description(
        {
            androidx.compose.material3.Text(text = "Description")
        },
        {
            androidx.compose.material3.Icon(
                imageVector = Icons.AutoMirrored.Filled.List,
                contentDescription = "description icon"
            )
        },
        {
            androidx.compose.material3.Text(
                text = it.viewState.value.lessonInfo.data?.room ?: "fff"
            )
        }
    ),
    Marks(
        {
            androidx.compose.material3.Text(text = "Marks")
        },
        {
            androidx.compose.material3.Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "marks icon"
            )
        },
        {
            androidx.compose.material3.Text(
                text = it.viewState.value.lessonInfo.data?.marks.toString()
            )
        }
    ),
    Homework(
        {
            androidx.compose.material3.Text(text = "Homework")
        },
        {
            androidx.compose.material3.Icon(
                imageVector = Icons.Default.Create,
                contentDescription = "homework icon"
            )
        },
        {
            androidx.compose.material3.Text(
                text = it.viewState.value.lessonInfo.data?.homeworks.toString()
            )
        }
    )
}