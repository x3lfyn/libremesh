package com.vobbla16.mesh

import androidx.compose.material3.FabPosition
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import com.vobbla16.mesh.ui.ViewState

data class MainActivityState(
    val topBar: (@Composable () -> Unit)?,
    val showBottomBar: Boolean,
    val fab: (@Composable () -> Unit)?,
    val fabPosition: FabPosition,
    val snackbarHostState: SnackbarHostState
): ViewState
