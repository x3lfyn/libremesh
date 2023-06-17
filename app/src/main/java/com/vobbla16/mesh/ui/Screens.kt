package com.vobbla16.mesh.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

enum class Screens(val route: String) {
    Schedule("schedule"),
    Marks("marks"),
    Login("login"),
    Profile("profile"),
    Homework("homework")
}


enum class NavBarItems(val screen: Screens, val label: String, val icon: @Composable () -> Unit) {
    Schedule(
        Screens.Schedule,
        "Schedule",
        { Icon(imageVector = Icons.Default.List, contentDescription = "list icon") }
    ),
    Marks(
        Screens.Marks,
        "Marks",
        { Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "marks icon") }
    ),
    Homework(
        Screens.Homework,
        "Homework",
        { Icon(imageVector = Icons.Default.Create, contentDescription = "homework icon") }
    ),
    Profile(
        Screens.Profile,
        "Profile",
        { Icon(imageVector = Icons.Default.Person, contentDescription = "profile icon") }
    )
}