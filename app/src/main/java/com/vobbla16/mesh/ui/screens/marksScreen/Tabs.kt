package com.vobbla16.mesh.ui.screens.marksScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vobbla16.mesh.R
import com.vobbla16.mesh.ui.screens.marksScreen.subscreens.ShowByDate
import com.vobbla16.mesh.ui.screens.marksScreen.subscreens.ShowBySubject
import com.vobbla16.mesh.ui.screens.marksScreen.subscreens.ShowRating

enum class Tabs @OptIn(ExperimentalFoundationApi::class) constructor(
    val title: String,
    val icon: @Composable () -> Unit,
    val subscreen: @Composable (MarksScreenViewModel, DestinationsNavigator) -> Unit
) {
    @OptIn(ExperimentalFoundationApi::class)
    @SuppressLint("NewApi")
    ShowByDate("By date", {
        Icon(
            imageVector = Icons.Default.DateRange, contentDescription = "calendar icon"
        )
    }, { vm, _ -> ShowByDate(vm) }),

    @OptIn(ExperimentalFoundationApi::class)
    @SuppressLint("NewApi")
    ShowBySubject("By subject", {
        Icon(
            painter = painterResource(id = R.drawable.subject_24dp),
            contentDescription = "subject icon"
        )
    }, { vm, navigator -> ShowBySubject(vm, navigator) }),

    @OptIn(ExperimentalFoundationApi::class)
    ShowRating("Rating", {
        Icon(
            imageVector = Icons.Default.Star, contentDescription = "star icon"
        )
    }, { vm, _ -> ShowRating(vm) })
}