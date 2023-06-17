package com.vobbla16.mesh.ui.screens.profileScreen

import com.vobbla16.mesh.domain.model.profile.Child
import com.vobbla16.mesh.ui.ViewState

data class ProfileScreenState(
    val profile: Child?,
    val isLoading: Boolean,
    val error: String?,

    val dialogOpened: Boolean,
    val isLoggingOut: Boolean
): ViewState
