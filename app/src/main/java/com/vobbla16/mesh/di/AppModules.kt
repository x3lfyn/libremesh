package com.vobbla16.mesh.di

import androidx.compose.foundation.ExperimentalFoundationApi
import com.vobbla16.mesh.MainActivityViewModel
import com.vobbla16.mesh.data.remote.InsertAuthKtorPlugin
import com.vobbla16.mesh.data.repository.MeshRepositoryImpl
import com.vobbla16.mesh.data.settingsStore.SettingsRepositoryImpl
import com.vobbla16.mesh.domain.repository.MeshRepository
import com.vobbla16.mesh.domain.repository.SettingsRepository
import com.vobbla16.mesh.domain.use_case.GetClassmatesUseCase
import com.vobbla16.mesh.domain.use_case.GetHomeworkUseCase
import com.vobbla16.mesh.domain.use_case.GetHomeworkWithLessonUseCase
import com.vobbla16.mesh.domain.use_case.GetLessonInfoUseCase
import com.vobbla16.mesh.domain.use_case.GetMarksForSubjectUseCase
import com.vobbla16.mesh.domain.use_case.GetMarksReportUseCase
import com.vobbla16.mesh.domain.use_case.GetRatingClassDeanonUseCase
import com.vobbla16.mesh.domain.use_case.GetRatingClassUseCase
import com.vobbla16.mesh.domain.use_case.GetScheduleItemIdFromMarkUseCase
import com.vobbla16.mesh.domain.use_case.GetScheduleUseCase
import com.vobbla16.mesh.domain.use_case.GetShortScheduleUseCase
import com.vobbla16.mesh.domain.use_case.GetStudentUseCase
import com.vobbla16.mesh.domain.use_case.GetTokenUseCase
import com.vobbla16.mesh.domain.use_case.LogOutUseCase
import com.vobbla16.mesh.domain.use_case.MarkHomeworkDoneUseCase
import com.vobbla16.mesh.domain.use_case.OauthCodeToTokenUseCase
import com.vobbla16.mesh.ui.screens.homeworkScreen.HomeworkScreenViewModel
import com.vobbla16.mesh.ui.screens.lessonScreen.LessonScreenViewModel
import com.vobbla16.mesh.ui.screens.loginScreen.LoginScreenViewModel
import com.vobbla16.mesh.ui.screens.marksScreen.MarksScreenViewModel
import com.vobbla16.mesh.ui.screens.profileScreen.ProfileScreenViewModel
import com.vobbla16.mesh.ui.screens.scheduleScreen.ScheduleScreenViewModel
import com.vobbla16.mesh.ui.screens.subjectScreen.SubjectScreenViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@OptIn(ExperimentalFoundationApi::class)
val appModule = module {
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(InsertAuthKtorPlugin) {
                getTokenUseCase = get<GetTokenUseCase>()
            }
        }
    }

    single<MeshRepository> { MeshRepositoryImpl() }
    single<SettingsRepository> { SettingsRepositoryImpl(androidContext()) }

    single { OauthCodeToTokenUseCase(get()) }
    single { GetTokenUseCase(get()) }
    single { GetScheduleUseCase(get()) }
    single { GetMarksReportUseCase(get()) }
    single { LogOutUseCase(get()) }
    single { GetHomeworkUseCase(get()) }
    single { GetStudentUseCase(get()) }
    single { GetLessonInfoUseCase(get()) }
    single { GetClassmatesUseCase(get(), get()) }
    single { GetRatingClassUseCase(get(), get()) }
    single { GetRatingClassDeanonUseCase(get(), get()) }
    single { GetShortScheduleUseCase(get()) }
    single { GetHomeworkWithLessonUseCase(get(), get()) }
    single { GetScheduleItemIdFromMarkUseCase(get()) }
    single { MarkHomeworkDoneUseCase(get()) }
    single { GetMarksForSubjectUseCase(get()) }

    viewModel {
        ScheduleScreenViewModel(get())
    }
    viewModel {
        LoginScreenViewModel(get(), get())
    }
    viewModel {
        MarksScreenViewModel(get(), get(), get())
    }
    viewModel {
        ProfileScreenViewModel(get(), get())
    }
    viewModel {
        HomeworkScreenViewModel(get(), get())
    }
    viewModel {
        MainActivityViewModel()
    }
    viewModel {
        LessonScreenViewModel(get(), get())
    }
    viewModel {
        SubjectScreenViewModel(get(), get())
    }
}
