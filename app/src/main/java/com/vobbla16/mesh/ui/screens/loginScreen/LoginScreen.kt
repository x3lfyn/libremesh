package com.vobbla16.mesh.ui.screens.loginScreen

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LoginScreen(navController: NavController, mainVM: MainActivityViewModel) {
    val vm: LoginScreenViewModel = koinViewModel()
    val state = vm.viewState.value

    LaunchedEffect(key1 = null) {
        mainVM.updateState {
            copy(
                topBar = null,
                showBottomBar = false,
                fab = null
            )
        }
    }

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

    SideEffect {
        Log.d("RECOMPS", "LoginScreen recomposition occurred")
    }

    Column {
        when (state.processingState) {
            is ProcessingState.WebViewStep -> {
                val wvState =
                    rememberWebViewState(url = Constants.MOSRU_OAUTH_URL)

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
                Text(
                    text = "Error occurred: ${state.processingState.message}",
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}