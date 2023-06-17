package com.vobbla16.mesh

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.SideEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vobbla16.mesh.ui.MainScaffold
import com.vobbla16.mesh.ui.Screens
import com.vobbla16.mesh.ui.screens.homeworkScreen.HomeworkScreen
import com.vobbla16.mesh.ui.screens.loginScreen.LoginScreen
import com.vobbla16.mesh.ui.screens.marksScreen.MarksScreen
import com.vobbla16.mesh.ui.screens.profileScreen.ProfileScreen
import com.vobbla16.mesh.ui.screens.scheduleScreen.ScheduleScreen
import com.vobbla16.mesh.ui.theme.MeshTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeshTheme {
                val navController = rememberNavController()

                MainScaffold(navController) { scaffoldController ->
                    SideEffect {
                        Log.d("RECOMPS", "MainActivity recomposition occurred")
                    }
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Schedule.route
                    ) {
                        composable(Screens.Schedule.route) {
                            ScheduleScreen(navController, scaffoldController)
                        }
                        composable(Screens.Marks.route) {
                            MarksScreen(navController, scaffoldController)
                        }
                        composable(Screens.Login.route) {
                            LoginScreen(navController, scaffoldController)
                        }
                        composable(Screens.Profile.route) {
                            ProfileScreen(navController, scaffoldController)
                        }
                        composable(Screens.Homework.route) {
                            HomeworkScreen(navController, scaffoldController)
                        }
                    }
                }
            }
        }
    }
}
