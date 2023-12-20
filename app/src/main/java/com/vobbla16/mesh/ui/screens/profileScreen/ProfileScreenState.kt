package com.vobbla16.mesh.ui.screens.profileScreen

import com.vobbla16.mesh.domain.model.profile.Child
import com.vobbla16.mesh.ui.ViewState
import com.vobbla16.mesh.ui.genericHolder.GenericHolder

data class ProfileScreenState(
    val childData: GenericHolder<Child>,

    val dialogOpened: Boolean,
    val isLoggingOut: Boolean
): ViewState
