package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class GetTokenUseCase(private val settingsRepository: SettingsRepository) {
    operator fun invoke(): String? = runBlocking {
        val tokenFlow = settingsRepository.token

        return@runBlocking tokenFlow.first()
    }
}