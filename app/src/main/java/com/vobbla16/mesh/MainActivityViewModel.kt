package com.vobbla16.mesh

import androidx.compose.material3.FabPosition
import androidx.compose.material3.SnackbarHostState
import com.vobbla16.mesh.ui.BaseViewModel

class MainActivityViewModel: BaseViewModel<MainActivityState, Nothing>() {
    override fun setInitialState() = MainActivityState(
        topBar = null,
        showBottomBar = false,
        fab = null,
        fabPosition = FabPosition.End,
        snackbarHostState = SnackbarHostState()
    )

    fun updateState(reducer: MainActivityState.() -> MainActivityState) = setState(reducer)
}