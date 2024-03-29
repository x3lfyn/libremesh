package com.vobbla16.mesh.ui

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.vobbla16.mesh.R
import com.vobbla16.mesh.ui.screens.destinations.HomeworkScreenDestination
import com.vobbla16.mesh.ui.screens.destinations.MarksScreenDestination
import com.vobbla16.mesh.ui.screens.destinations.ProfileScreenDestination
import com.vobbla16.mesh.ui.screens.destinations.ScheduleScreenDestination

enum class NavBarItems(
    val screen: DirectionDestinationSpec,
    val label: (context: Context) -> String,
    val inactiveIcon: @Composable () -> Unit,
    val activeIcon: @Composable () -> Unit
) {
    Schedule(
        ScheduleScreenDestination,
        { it.getString(R.string.bottom_bar_schedule_screen) },
        { Icon(imageVector = Icons.AutoMirrored.Outlined.List, contentDescription = "list outlined icon") },
        { Icon(imageVector = Icons.AutoMirrored.Filled.List, contentDescription = "list filled icon") }
    ),
    @OptIn(ExperimentalFoundationApi::class)
    Marks(
        MarksScreenDestination,
        { it.getString(R.string.bottom_bar_marks_screen) },
        { Icon(imageVector = Icons.Outlined.CheckCircle, contentDescription = "marks outlined icon") },
        { Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "marks filled icon") }
    ),
    Homework(
        HomeworkScreenDestination,
        { it.getString(R.string.bottom_bar_homework_screen) },
        { Icon(imageVector = Icons.Outlined.Create, contentDescription = "homework outlined icon") },
        { Icon(imageVector = Icons.Filled.Create, contentDescription = "homework filled icon") }
    ),
    Profile(
        ProfileScreenDestination,
        { it.getString(R.string.bottom_bar_profile_screen) },
        { Icon(imageVector = Icons.Outlined.Person, contentDescription = "profile outlined icon") },
        { Icon(imageVector = Icons.Filled.Person, contentDescription = "profile filled icon") }
    )
}