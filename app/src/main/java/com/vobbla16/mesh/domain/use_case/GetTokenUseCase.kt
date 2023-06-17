package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.first

class GetTokenUseCase(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(): String? {
        val tokenFlow = settingsRepository.token

        // for the future. validate and update token here

        return tokenFlow.first()
    }
}