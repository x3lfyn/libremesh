package com.vobbla16.mesh.domain.use_case

import com.vobbla16.mesh.common.structures.LoadingOrDone
import com.vobbla16.mesh.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LogOutUseCase(private val settingsRepository: SettingsRepository) {
    operator fun invoke(): Flow<LoadingOrDone> = flow {
        emit(LoadingOrDone.Loading)
        settingsRepository.removeToken()
        emit(LoadingOrDone.Done)
    }
}