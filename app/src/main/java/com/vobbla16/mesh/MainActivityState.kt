package com.vobbla16.mesh

import androidx.compose.material3.SnackbarHostState
import com.vobbla16.mesh.ui.ViewState

data class MainActivityState(
    val showBottomBar: Boolean,
    val snackbarHostState: SnackbarHostState
): ViewState
