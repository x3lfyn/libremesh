package com.vobbla16.mesh.ui.screens.homeworkScreen

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vobbla16.mesh.LocalMainVM
import com.vobbla16.mesh.R
import com.vobbla16.mesh.domain.model.homework.HomeworkItem
import com.vobbla16.mesh.domain.model.homework.HomeworkItemsForDateWithLessonModel
import com.vobbla16.mesh.ui.commonComponents.genericHolderContainer.GenericHolderContainer
import com.vobbla16.mesh.ui.screens.destinations.LessonScreenDestination
import com.vobbla16.mesh.ui.screens.destinations.LoginScreenDestination
import com.vobbla16.mesh.ui.screens.homeworkScreen.components.HomeworkItemsForDateCard
import com.vobbla16.mesh.ui.screens.lessonScreen.OpenTab
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun HomeworkScreen(
    navigator: DestinationsNavigator
) {
    val mainVM = LocalMainVM.current
    val vm: HomeworkScreenViewModel = koinViewModel()
    val state = vm.viewState.value

    LaunchedEffect(key1 = null) {
        mainVM.showBottomBar()
    }

    val vmActionsScope = rememberCoroutineScope()
    LaunchedEffect(key1 = vm.action) {
        vmActionsScope.launch {
            vm.action.onEach { action ->
                when (action) {
                    is HomeworkScreenAction.ShowMarkDoneError -> {
                        vmActionsScope.launch {
                            mainVM.viewState.value.snackbarHostState.showSnackbar(action.e.toString())
                        }
                    }
                    is HomeworkScreenAction.NavigateToLoginScreen -> {
                        navigator.navigate(LoginScreenDestination)
                    }
                }
            }.collect()
        }
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.homework_screen_title)) }, scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = WindowInsets.statusBars
    ) { paddingValues ->
        GenericHolderContainer(
            holder = state.homeworkData,
            onRefresh = { vm.refreshData() },
            onRetry = { vm.retryOnError() },
            modifier = Modifier.padding(paddingValues)
        ) {
            HomeworkScreenUI(it, state.loadingDoneIds, mainVM.viewState.value.snackbarHostState, navigator) { homeworkItem ->
                vm.toggleDone(homeworkItem)
            }
        }
    }
}

@Composable
fun HomeworkScreenUI(
    data: List<HomeworkItemsForDateWithLessonModel>,
    loadingDoneIds: List<Long>,
    snackbarHostState: SnackbarHostState,
    navigator: DestinationsNavigator,
    onCheckDone: (HomeworkItem) -> Unit
) {
    LazyColumn {
        items(data) { item ->
            HomeworkItemsForDateCard(
                data = item,
                modifier = Modifier.padding(4.dp),
                snackbarHostState = snackbarHostState,
                onClick = {
                    navigator.navigate(
                        LessonScreenDestination(
                            lessonSelector = it,
                            openTab = OpenTab.Homeworks
                        )
                    )
                },
                loadingDoneIds = loadingDoneIds,
                onCheckDone = onCheckDone
            )
        }
    }
}
//
//@Preview(showBackground = true, name = "First loading preview")
//@Composable
//fun HomeworkScreenUIPreview1() {
//    HomeworkScreenUI(
//        state = HomeworkScreenState(
//            pagingFlow = null,
//            isLoading = true,
//            error = null
//        )
//    )
//}
//
//@Preview(showBackground = true, name = "First error preview")
//@Composable
//fun HomeworkScreenUIPreview2() {
//    HomeworkScreenUI(
//        state = HomeworkScreenState(
//            pagingFlow = null,
//            isLoading = false,
//            error = "Some error occurred"
//        )
//    )
//}
//
//@Preview(showBackground = true, name = "Refresh loading preview")
//@Composable
//fun HomeworkScreenUIPreview3() {
//    HomeworkScreenUI(
//        state = HomeworkScreenState(
//            pagingFlow = flowOf(
//                PagingData.from(
//                    data = emptyList(),
//                    sourceLoadStates = LoadStates(
//                        refresh = LoadState.Loading,
//                        append = LoadState.NotLoading(false),
//                        prepend = LoadState.NotLoading(false)
//                    )
//                )
//            ),
//            isLoading = false,
//            error = null
//        )
//    )
//}
//
//@Preview(showBackground = true, name = "Refresh error preview")
//@Composable
//fun HomeworkScreenUIPreview4() {
//    HomeworkScreenUI(
//        state = HomeworkScreenState(
//            pagingFlow = flowOf(
//                PagingData.from(
//                    data = emptyList(),
//                    sourceLoadStates = LoadStates(
//                        refresh = LoadState.Error(Throwable("Some error occurred")),
//                        append = LoadState.NotLoading(false),
//                        prepend = LoadState.NotLoading(false)
//                    )
//                )
//            ),
//            isLoading = false,
//            error = null
//        )
//    )
//}
//
//@Preview(showBackground = true, name = "Loaded preview")
//@Composable
//fun HomeworkScreenUIPreview5() {
//    HomeworkScreenUI(
//        state = HomeworkScreenState(
//            pagingFlow = flowOf(
//                PagingData.from(
//                    data = listOf(
//                        HomeworkItemsForDateModel(
//                            LocalDate(2022, 5, 11),
//                            listOf(
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Физика",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 11)
//                                ),
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Очень длинное название Очень длинное название Очень длинное название Очень длинное название Очень длинное название",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 11)
//                                ),
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Физика",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 11)
//                                )
//                            )
//                        ),
//                        HomeworkItemsForDateModel(
//                            LocalDate(2022, 5, 15),
//                            listOf(
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Физика",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 15)
//                                ),
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Очень длинное название Очень длинное название",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 15)
//                                ),
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Физика",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 15)
//                                )
//                            )
//                        )
//                    ),
//                    sourceLoadStates = LoadStates(
//                        refresh = LoadState.NotLoading(false),
//                        append = LoadState.NotLoading(false),
//                        prepend = LoadState.NotLoading(false)
//                    )
//                )
//            ),
//            isLoading = false,
//            error = null
//        )
//    )
//}
//
//@Preview(showBackground = true, name = "Append loading preview")
//@Composable
//fun HomeworkScreenUIPreview6() {
//    HomeworkScreenUI(
//        state = HomeworkScreenState(
//            pagingFlow = flowOf(
//                PagingData.from(
//                    data = listOf(
//                        HomeworkItemsForDateModel(
//                            LocalDate(2022, 5, 11),
//                            listOf(
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Физика",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 11)
//                                ),
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Очень длинное название Очень длинное название Очень длинное название Очень длинное название Очень длинное название",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 11)
//                                ),
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Физика",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 11)
//                                )
//                            )
//                        ),
//                        HomeworkItemsForDateModel(
//                            LocalDate(2022, 5, 15),
//                            listOf(
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Физика",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 15)
//                                ),
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Очень длинное название Очень длинное название",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 15)
//                                ),
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Физика",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 15)
//                                )
//                            )
//                        )
//                    ),
//                    sourceLoadStates = LoadStates(
//                        refresh = LoadState.NotLoading(false),
//                        append = LoadState.Loading,
//                        prepend = LoadState.NotLoading(false)
//                    )
//                )
//            ),
//            isLoading = false,
//            error = null
//        )
//    )
//}
//
//@Preview(showBackground = true, name = "Append error preview")
//@Composable
//fun HomeworkScreenUIPreview7() {
//    HomeworkScreenUI(
//        state = HomeworkScreenState(
//            pagingFlow = flowOf(
//                PagingData.from(
//                    data = listOf(
//                        HomeworkItemsForDateModel(
//                            LocalDate(2022, 5, 11),
//                            listOf(
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Физика",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 11)
//                                ),
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Очень длинное название Очень длинное название Очень длинное название Очень длинное название Очень длинное название",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 11)
//                                ),
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Физика",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 11)
//                                )
//                            )
//                        ),
//                        HomeworkItemsForDateModel(
//                            LocalDate(2022, 5, 15),
//                            listOf(
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Физика",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 15)
//                                ),
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Очень длинное название Очень длинное название",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 15)
//                                ),
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Физика",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 15)
//                                )
//                            )
//                        )
//                    ),
//                    sourceLoadStates = LoadStates(
//                        refresh = LoadState.NotLoading(false),
//                        append = LoadState.Error(Throwable("Some error occurred")),
//                        prepend = LoadState.NotLoading(false)
//                    )
//                )
//            ),
//            isLoading = false,
//            error = null
//        )
//    )
//}
//
//@Preview(showBackground = true, name = "Prepend loading preview")
//@Composable
//fun HomeworkScreenUIPreview8() {
//    HomeworkScreenUI(
//        state = HomeworkScreenState(
//            pagingFlow = flowOf(
//                PagingData.from(
//                    data = listOf(
//                        HomeworkItemsForDateModel(
//                            LocalDate(2022, 5, 11),
//                            listOf(
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Физика",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 11)
//                                ),
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Очень длинное название Очень длинное название Очень длинное название Очень длинное название Очень длинное название",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 11)
//                                ),
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Физика",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 11)
//                                )
//                            )
//                        ),
//                        HomeworkItemsForDateModel(
//                            LocalDate(2022, 5, 15),
//                            listOf(
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Физика",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 15)
//                                ),
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Очень длинное название Очень длинное название",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 15)
//                                ),
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Физика",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 15)
//                                )
//                            )
//                        )
//                    ),
//                    sourceLoadStates = LoadStates(
//                        refresh = LoadState.NotLoading(false),
//                        append = LoadState.NotLoading(false),
//                        prepend = LoadState.Loading
//                    )
//                )
//            ),
//            isLoading = false,
//            error = null
//        )
//    )
//}
//
//@Preview(showBackground = true, name = "Prepend error preview")
//@Composable
//fun HomeworkScreenUIPreview9() {
//    HomeworkScreenUI(
//        state = HomeworkScreenState(
//            pagingFlow = flowOf(
//                PagingData.from(
//                    data = listOf(
//                        HomeworkItemsForDateModel(
//                            LocalDate(2022, 5, 11),
//                            listOf(
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Физика",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 11)
//                                ),
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Очень длинное название Очень длинное название Очень длинное название Очень длинное название Очень длинное название",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 11)
//                                ),
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Физика",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 11)
//                                )
//                            )
//                        ),
//                        HomeworkItemsForDateModel(
//                            LocalDate(2022, 5, 15),
//                            listOf(
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Физика",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 15)
//                                ),
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Очень длинное название Очень длинное название",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 15)
//                                ),
//                                HomeworkItem(
//                                    isReady = true,
//                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
//                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
//                                    subjectId = 1337,
//                                    subjectName = "Физика",
//                                    teacherId = 1337,
//                                    dateAssignedOn = LocalDate(2022, 5, 10),
//                                    datePreparedFor = LocalDate(2022, 5, 15)
//                                )
//                            )
//                        )
//                    ),
//                    sourceLoadStates = LoadStates(
//                        refresh = LoadState.NotLoading(false),
//                        append = LoadState.NotLoading(false),
//                        prepend = LoadState.Error(Throwable("Some error occurred"))
//                    )
//                )
//            ),
//            isLoading = false,
//            error = null
//        )
//    )
//}