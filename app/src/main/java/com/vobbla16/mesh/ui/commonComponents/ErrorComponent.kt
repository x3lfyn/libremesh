package com.vobbla16.mesh.ui.commonComponents

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vobbla16.mesh.R
import com.vobbla16.mesh.common.toText

@Composable
fun ErrorComponent(
    e: Throwable,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(e) {
        Log.e("AppErrors", e.toText())
        Log.e("AppErrors", e.stackTraceToString())
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.error_occurred),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(4.dp)
        )
        Text(
            text = e.toText(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
        Button(onClick = onRetry, modifier = Modifier.padding(6.dp)) {
            Text(text = stringResource(R.string.error_component_retry_btn))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorComponentPreview1() {
    ErrorComponent(
        e = java.net.UnknownHostException("Unable to resolve host \"school.mos.ru\": No address associated with hostname"),
        onRetry = {})
}