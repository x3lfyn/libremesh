package com.vobbla16.mesh.ui.screens.loginScreen

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.vobbla16.mesh.MainActivityViewModel
import com.vobbla16.mesh.common.Constants
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LoginScreen(navController: NavController, mainVM: MainActivityViewModel) {
    val vm: LoginScreenViewModel = koinViewModel()
    val state = vm.viewState.value

    val vmActionsScope = rememberCoroutineScope()
    LaunchedEffect(key1 = vm.action) {
        vmActionsScope.launch {
            vm.action.collect { action ->
                when (action) {
                    is LoginScreenAction.SetSavedStateFlag -> {
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            "loggedIn",
                            true
                        )
                    }

                    is LoginScreenAction.GoBack -> {
                        navController.popBackStack()
                    }
                }
            }
        }
    }

    LaunchedEffect(null) {
        mainVM.hideBottomBar()
    }

    SideEffect {
        Log.d("RECOMPS", "LoginScreen recomposition occurred")
    }

    when (state.otherState.processingState) {
        is ProcessingState.WelcomeStep -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome to libremesh",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(8.dp)
                )
                Button(onClick = { vm.toWebViewStep() }) {
                    Text(text = "Login")
                }
            }
        }

        is ProcessingState.WebViewStep -> {
            val wvState =
                rememberWebViewState(url = Constants.MOSRU_OAUTH_URL)

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Column {
                                Text(text = wvState.pageTitle ?: "Loading...", maxLines = 1)
                                Text(
                                    text = wvState.lastLoadedUrl ?: "Loading...",
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 1
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = { vm.backFromWebViewStep() }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "go back icon"
                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->
                Column(Modifier.padding(paddingValues)) {
                    val webViewClient = remember {
                        object : AccompanistWebViewClient() {
                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                url: String?
                            ): Boolean {
                                if (url != null && url.startsWith(Constants.OAUTH_CALLBACK_PREFIX)) {
                                    vm.processCode(url.removePrefix(Constants.OAUTH_CALLBACK_PREFIX))
                                    return true
                                }
                                return false
                            }
                        }
                    }

                    WebView(state = wvState, modifier = Modifier.fillMaxSize(), onCreated = {
                        it.settings.javaScriptEnabled = true
                    }, client = webViewClient)
                }
            }
        }

        is ProcessingState.ProcessingStep -> {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Text(
                    text = "Processing oauth code",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }

        is ProcessingState.Error -> {
            Column {
                Text(
                    text = "Error occurred: ${state.otherState.processingState.message}",
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}