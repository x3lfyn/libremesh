package com.vobbla16.mesh.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

enum class Screens(val route: String) {
    Schedule("schedule"),
    Marks("marks"),
    Login("login"),
    Profile("profile"),
    Homework("homework")
}


enum class NavBarItems(
    val screen: Screens,
    val label: String,
    val inactiveIcon: @Composable () -> Unit,
    val activeIcon: @Composable () -> Unit
) {
    Schedule(
        Screens.Schedule,
        "Schedule",
        { Icon(imageVector = Icons.Outlined.List, contentDescription = "list outlined icon") },
        { Icon(imageVector = Icons.Filled.List, contentDescription = "list filled icon") }
    ),
    Marks(
        Screens.Marks,
        "Marks",
        { Icon(imageVector = Icons.Outlined.CheckCircle, contentDescription = "marks outlined icon") },
        { Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "marks filled icon") }
    ),
    Homework(
        Screens.Homework,
        "Homework",
        { Icon(imageVector = Icons.Outlined.Create, contentDescription = "homework outlined icon") },
        { Icon(imageVector = Icons.Filled.Create, contentDescription = "homework filled icon") }
    ),
    Profile(
        Screens.Profile,
        "Profile",
        { Icon(imageVector = Icons.Outlined.Person, contentDescription = "profile outlined icon") },
        { Icon(imageVector = Icons.Filled.Person, contentDescription = "profile filled icon") }
    )
}