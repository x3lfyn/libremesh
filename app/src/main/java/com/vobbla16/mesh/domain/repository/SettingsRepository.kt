package com.vobbla16.mesh.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val token: Flow<String?>
    suspend fun setToken(token: String)
    suspend fun removeToken()
}