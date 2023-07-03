package com.vobbla16.mesh.di

import com.vobbla16.mesh.MainActivityViewModel
import com.vobbla16.mesh.data.repository.MeshRepositoryImpl
import com.vobbla16.mesh.data.settingsStore.SettingsRepositoryImpl
import com.vobbla16.mesh.domain.repository.MeshRepository
import com.vobbla16.mesh.domain.repository.SettingsRepository
import com.vobbla16.mesh.domain.use_case.GetHomeworkUseCase
import com.vobbla16.mesh.domain.use_case.GetMarksReportUseCase
import com.vobbla16.mesh.domain.use_case.GetScheduleUseCase
import com.vobbla16.mesh.domain.use_case.GetStudentIntUseCase
import com.vobbla16.mesh.domain.use_case.GetStudentUseCase
import com.vobbla16.mesh.domain.use_case.GetTokenUseCase
import com.vobbla16.mesh.domain.use_case.LogOutUseCase
import com.vobbla16.mesh.domain.use_case.OauthCodeToTokenUseCase
import com.vobbla16.mesh.ui.screens.homeworkScreen.HomeworkScreenViewModel
import com.vobbla16.mesh.ui.screens.loginScreen.LoginScreenViewModel
import com.vobbla16.mesh.ui.screens.marksScreen.MarksScreenViewModel
import com.vobbla16.mesh.ui.screens.profileScreen.ProfileScreenViewModel
import com.vobbla16.mesh.ui.screens.scheduleScreen.ScheduleScreenViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json()
            }
        }
    }

    single<MeshRepository> { MeshRepositoryImpl() }
    single<SettingsRepository> { SettingsRepositoryImpl(androidContext()) }

    single { OauthCodeToTokenUseCase(get()) }
    single { GetTokenUseCase(get()) }
    single { GetScheduleUseCase(get(), get(), get()) }
    single { GetStudentIntUseCase(get()) }
    single { GetMarksReportUseCase(get(), get(), get()) }
    single { GetStudentUseCase(get(), get()) }
    single { LogOutUseCase(get()) }
    single { GetHomeworkUseCase(get(), get(), get()) }

    viewModel {
        ScheduleScreenViewModel(get())
    }
    viewModel {
        LoginScreenViewModel(get(), get())
    }
    viewModel {
        MarksScreenViewModel(get())
    }
    viewModel {
        ProfileScreenViewModel(get(), get())
    }
    viewModel {
        HomeworkScreenViewModel(get())
    }
    viewModel {
        MainActivityViewModel()
    }
}
