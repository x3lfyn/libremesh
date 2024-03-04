package com.vobbla16.mesh.ui.screens.lessonScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import com.vobbla16.mesh.ui.screens.lessonScreen.subscreens.DescriptionTab
import com.vobbla16.mesh.ui.screens.lessonScreen.subscreens.HomeworkTab
import com.vobbla16.mesh.ui.screens.lessonScreen.subscreens.MarksTab

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
        { DescriptionTab(it) }
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
            MarksTab(it)
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
            HomeworkTab(it)
        }
    )
}