package com.vobbla16.mesh.ui.screens.loginScreen

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.vobbla16.mesh.LocalMainVM
import com.vobbla16.mesh.R
import com.vobbla16.mesh.common.Constants
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
@Destination
fun LoginScreen(resultNavigator: ResultBackNavigator<Boolean>) {
    val mainVM = LocalMainVM.current
    val vm: LoginScreenViewModel = koinViewModel()
    val state = vm.viewState.value

    val vmActionsScope = rememberCoroutineScope()
    LaunchedEffect(key1 = vm.action) {
        vmActionsScope.launch {
            vm.action.collect { action ->
                when (action) {
                    is LoginScreenAction.SetSavedStateFlag -> {
                        resultNavigator.setResult(true)
                    }

                    is LoginScreenAction.GoBack -> {
                        resultNavigator.navigateBack()
                    }
                }
            }
        }
    }

    LaunchedEffect(null) {
        mainVM.hideBottomBar()
    }

    when (state.processingState) {
        is ProcessingState.WelcomeStep -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.login_screen_welcome_to_libremesh),
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
                                Text(text = wvState.pageTitle ?: stringResource(R.string.login_screen_webview_loading), maxLines = 1)
                                Text(
                                    text = wvState.lastLoadedUrl ?: stringResource(R.string.login_screen_webview_loading),
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 1
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = { vm.backFromWebViewStep() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                    text = stringResource(R.string.login_screen_processing_oauth_code),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }

        is ProcessingState.Error -> {
            Column {
                Text(
                    text = "${stringResource(R.string.error_occurred)}: ${state.processingState.message}",
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}