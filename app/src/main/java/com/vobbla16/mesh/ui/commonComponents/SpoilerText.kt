package com.vobbla16.mesh.ui.commonComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skydoves.cloudy.Cloudy

@Composable
fun SpoilerText(
    modifier: Modifier = Modifier,
    isMasked: Boolean = true,
    innerText: @Composable () -> Unit
) {
    var masked by remember {
        mutableStateOf(isMasked)
    }

    @Composable
    fun innerContent() {
        Box(Modifier.padding(4.dp, 2.dp)) {
            innerText()
        }
    }

    Box(modifier.clickable {
        masked = !masked
    }) {
        if (masked) {
            Cloudy(radius = 25) {
                innerContent()
            }
        } else {
            innerContent()
        }
    }
}