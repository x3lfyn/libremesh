package com.vobbla16.mesh.ui.commonComponents.genericHolderContainer

import android.view.animation.PathInterpolator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vobbla16.mesh.ui.commonComponents.ErrorComponent
import com.vobbla16.mesh.ui.commonComponents.pullrefresh.PullRefreshIndicator
import com.vobbla16.mesh.ui.commonComponents.pullrefresh.pullRefresh
import com.vobbla16.mesh.ui.commonComponents.pullrefresh.rememberPullRefreshState
import com.vobbla16.mesh.ui.genericHolder.GenericHolder
import com.vobbla16.mesh.ui.genericHolder.LoadingState
import io.ktor.serialization.JsonConvertException

@Composable
fun <T> GenericHolderContainer(
    holder: GenericHolder<T>,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    useAnimations: Boolean = true,
    content: @Composable (T) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(holder.isRefreshing, onRefresh)

    Box(
        modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
    ) {
        holder.error?.let {
            ErrorComponent(it, onRetry)
        } ?: run {
            if (holder.isLoading) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            } else {
                if (useAnimations) {
                    AnimatedVisibility(
                        visible = holder.data != null,
                        enter = slideInVertically(
                            initialOffsetY = { it / 3 },
                            animationSpec = tween(
                                durationMillis = 250,
                                easing = LinearOutSlowInEasing
                            )
                        ) + fadeIn(
                            animationSpec = tween(
                                durationMillis = 250,
                                easing = FastOutSlowInEasing
                            )
                        ),
                        exit = fadeOut(
                            animationSpec = tween(
                                durationMillis = 100,
                                easing = LinearEasing
                            )
                        )
                    ) {
                        holder.data?.let {
                            Column {
                                content(holder.data)
                            }
                        }
                    }
                } else {
                    holder.data?.let {
                        Column {
                            content(holder.data)
                        }
                    }
                }
            }
        }

        PullRefreshIndicator(
            refreshing = holder.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}