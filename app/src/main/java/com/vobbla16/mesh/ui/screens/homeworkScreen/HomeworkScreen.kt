package com.vobbla16.mesh.ui.screens.homeworkScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.vobbla16.mesh.MainActivityViewModel
import com.vobbla16.mesh.common.toText
import com.vobbla16.mesh.domain.model.homework.HomeworkItem
import com.vobbla16.mesh.domain.model.homework.HomeworkItemsForDate
import com.vobbla16.mesh.ui.screens.homeworkScreen.components.HomeworkItemsForDateCard
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeworkScreen(navController: NavController, mainVM: MainActivityViewModel) {
    val vm: HomeworkScreenViewModel = koinViewModel()
    val state = vm.viewState.value

    LaunchedEffect(key1 = null) {
        mainVM.updateState {
            copy(
                topBar = { CenterAlignedTopAppBar(title = { Text(text = "Homework screen") }) },
                showBottomBar = true,
                fab = null
            )
        }
    }

    HomeworkScreenUI(state = state)
}

@Composable
fun HomeworkScreenUI(state: HomeworkScreenState) {
    Column(Modifier.fillMaxSize()) {
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }

        state.error?.let { err ->
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = err,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        state.pagingFlow?.let { f ->
            val lazyPagingItems = f.collectAsLazyPagingItems()

            lazyPagingItems.loadState.refresh
                .takeIf { it !is LoadState.NotLoading }
                ?.let { loadState ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        when (loadState) {
                            is LoadState.Error -> {
                                Text(
                                    text = loadState.error.toText(),
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }

                            is LoadState.Loading -> {
                                CircularProgressIndicator(Modifier.align(Alignment.Center))
                            }

                            else -> {}
                        }
                    }
                }

            LazyColumn(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (val loadState = lazyPagingItems.loadState.prepend) {
                    is LoadState.Error -> {
                        item {
                            Text(
                                text = loadState.error.toText(),
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }

                    is LoadState.Loading -> {
                        item {
                            CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                            Text(
                                text = "Loading previous items",
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }

                    is LoadState.NotLoading -> {}
                }

                items(lazyPagingItems.itemCount) { i ->
                    val item = lazyPagingItems[i]!!

                    HomeworkItemsForDateCard(data = item, modifier = Modifier.padding(4.dp))
                }

                when (val loadState = lazyPagingItems.loadState.append) {
                    is LoadState.Error -> {
                        item {
                            Text(
                                text = loadState.error.toText(),
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }

                    is LoadState.Loading -> {
                        item {
                            CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                            Text(
                                text = "Loading next items",
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }

                    is LoadState.NotLoading -> {}
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "First loading preview")
@Composable
fun HomeworkScreenUIPreview1() {
    HomeworkScreenUI(
        state = HomeworkScreenState(
            pagingFlow = null,
            isLoading = true,
            error = null
        )
    )
}

@Preview(showBackground = true, name = "First error preview")
@Composable
fun HomeworkScreenUIPreview2() {
    HomeworkScreenUI(
        state = HomeworkScreenState(
            pagingFlow = null,
            isLoading = false,
            error = "Some error occurred"
        )
    )
}

@Preview(showBackground = true, name = "Refresh loading preview")
@Composable
fun HomeworkScreenUIPreview3() {
    HomeworkScreenUI(
        state = HomeworkScreenState(
            pagingFlow = flowOf(
                PagingData.from(
                    data = emptyList(),
                    sourceLoadStates = LoadStates(
                        refresh = LoadState.Loading,
                        append = LoadState.NotLoading(false),
                        prepend = LoadState.NotLoading(false)
                    )
                )
            ),
            isLoading = false,
            error = null
        )
    )
}

@Preview(showBackground = true, name = "Refresh error preview")
@Composable
fun HomeworkScreenUIPreview4() {
    HomeworkScreenUI(
        state = HomeworkScreenState(
            pagingFlow = flowOf(
                PagingData.from(
                    data = emptyList(),
                    sourceLoadStates = LoadStates(
                        refresh = LoadState.Error(Throwable("Some error occurred")),
                        append = LoadState.NotLoading(false),
                        prepend = LoadState.NotLoading(false)
                    )
                )
            ),
            isLoading = false,
            error = null
        )
    )
}

@Preview(showBackground = true, name = "Loaded preview")
@Composable
fun HomeworkScreenUIPreview5() {
    HomeworkScreenUI(
        state = HomeworkScreenState(
            pagingFlow = flowOf(
                PagingData.from(
                    data = listOf(
                        HomeworkItemsForDate(
                            LocalDate(2022, 5, 11),
                            listOf(
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Физика",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 11)
                                ),
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Очень длинное название Очень длинное название Очень длинное название Очень длинное название Очень длинное название",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 11)
                                ),
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Физика",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 11)
                                )
                            )
                        ),
                        HomeworkItemsForDate(
                            LocalDate(2022, 5, 15),
                            listOf(
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Физика",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 15)
                                ),
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Очень длинное название Очень длинное название",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 15)
                                ),
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Физика",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 15)
                                )
                            )
                        )
                    ),
                    sourceLoadStates = LoadStates(
                        refresh = LoadState.NotLoading(false),
                        append = LoadState.NotLoading(false),
                        prepend = LoadState.NotLoading(false)
                    )
                )
            ),
            isLoading = false,
            error = null
        )
    )
}

@Preview(showBackground = true, name = "Append loading preview")
@Composable
fun HomeworkScreenUIPreview6() {
    HomeworkScreenUI(
        state = HomeworkScreenState(
            pagingFlow = flowOf(
                PagingData.from(
                    data = listOf(
                        HomeworkItemsForDate(
                            LocalDate(2022, 5, 11),
                            listOf(
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Физика",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 11)
                                ),
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Очень длинное название Очень длинное название Очень длинное название Очень длинное название Очень длинное название",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 11)
                                ),
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Физика",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 11)
                                )
                            )
                        ),
                        HomeworkItemsForDate(
                            LocalDate(2022, 5, 15),
                            listOf(
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Физика",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 15)
                                ),
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Очень длинное название Очень длинное название",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 15)
                                ),
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Физика",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 15)
                                )
                            )
                        )
                    ),
                    sourceLoadStates = LoadStates(
                        refresh = LoadState.NotLoading(false),
                        append = LoadState.Loading,
                        prepend = LoadState.NotLoading(false)
                    )
                )
            ),
            isLoading = false,
            error = null
        )
    )
}

@Preview(showBackground = true, name = "Append error preview")
@Composable
fun HomeworkScreenUIPreview7() {
    HomeworkScreenUI(
        state = HomeworkScreenState(
            pagingFlow = flowOf(
                PagingData.from(
                    data = listOf(
                        HomeworkItemsForDate(
                            LocalDate(2022, 5, 11),
                            listOf(
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Физика",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 11)
                                ),
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Очень длинное название Очень длинное название Очень длинное название Очень длинное название Очень длинное название",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 11)
                                ),
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Физика",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 11)
                                )
                            )
                        ),
                        HomeworkItemsForDate(
                            LocalDate(2022, 5, 15),
                            listOf(
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Физика",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 15)
                                ),
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Очень длинное название Очень длинное название",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 15)
                                ),
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Физика",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 15)
                                )
                            )
                        )
                    ),
                    sourceLoadStates = LoadStates(
                        refresh = LoadState.NotLoading(false),
                        append = LoadState.Error(Throwable("Some error occurred")),
                        prepend = LoadState.NotLoading(false)
                    )
                )
            ),
            isLoading = false,
            error = null
        )
    )
}

@Preview(showBackground = true, name = "Prepend loading preview")
@Composable
fun HomeworkScreenUIPreview8() {
    HomeworkScreenUI(
        state = HomeworkScreenState(
            pagingFlow = flowOf(
                PagingData.from(
                    data = listOf(
                        HomeworkItemsForDate(
                            LocalDate(2022, 5, 11),
                            listOf(
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Физика",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 11)
                                ),
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Очень длинное название Очень длинное название Очень длинное название Очень длинное название Очень длинное название",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 11)
                                ),
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Физика",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 11)
                                )
                            )
                        ),
                        HomeworkItemsForDate(
                            LocalDate(2022, 5, 15),
                            listOf(
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Физика",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 15)
                                ),
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Очень длинное название Очень длинное название",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 15)
                                ),
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Физика",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 15)
                                )
                            )
                        )
                    ),
                    sourceLoadStates = LoadStates(
                        refresh = LoadState.NotLoading(false),
                        append = LoadState.NotLoading(false),
                        prepend = LoadState.Loading
                    )
                )
            ),
            isLoading = false,
            error = null
        )
    )
}

@Preview(showBackground = true, name = "Prepend error preview")
@Composable
fun HomeworkScreenUIPreview9() {
    HomeworkScreenUI(
        state = HomeworkScreenState(
            pagingFlow = flowOf(
                PagingData.from(
                    data = listOf(
                        HomeworkItemsForDate(
                            LocalDate(2022, 5, 11),
                            listOf(
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Физика",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 11)
                                ),
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Очень длинное название Очень длинное название Очень длинное название Очень длинное название Очень длинное название",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 11)
                                ),
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Физика",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 11)
                                )
                            )
                        ),
                        HomeworkItemsForDate(
                            LocalDate(2022, 5, 15),
                            listOf(
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Физика",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 15)
                                ),
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка Домашнее задание 2. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Очень длинное название Очень длинное название",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 15)
                                ),
                                HomeworkItem(
                                    isReady = true,
                                    createdAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    updatedAt = LocalDateTime(2022, 5, 10, 2, 2),
                                    description = "Домашнее задание 1. Возможно чуть длиннне, чем одна строка",
                                    subjectId = 1337,
                                    subjectName = "Физика",
                                    teacherId = 1337,
                                    dateAssignedOn = LocalDate(2022, 5, 10),
                                    datePreparedFor = LocalDate(2022, 5, 15)
                                )
                            )
                        )
                    ),
                    sourceLoadStates = LoadStates(
                        refresh = LoadState.NotLoading(false),
                        append = LoadState.NotLoading(false),
                        prepend = LoadState.Error(Throwable("Some error occurred"))
                    )
                )
            ),
            isLoading = false,
            error = null
        )
    )
}