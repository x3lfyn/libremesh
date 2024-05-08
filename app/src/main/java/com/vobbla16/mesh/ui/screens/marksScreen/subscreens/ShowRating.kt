package com.vobbla16.mesh.ui.screens.marksScreen.subscreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.vobbla16.mesh.R
import com.vobbla16.mesh.ui.commonComponents.genericHolderContainer.GenericHolderContainer
import com.vobbla16.mesh.ui.screens.marksScreen.MarksScreenViewModel
import com.vobbla16.mesh.ui.screens.marksScreen.components.ClassRatingItemCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowRating(vm: MarksScreenViewModel) {
    val state = vm.viewState.value

    GenericHolderContainer(
        holder = state.ratingClass,
        onRefresh = { vm.getRatingClass(true) },
        onRetry = { vm.getRatingClass(false) }
    ) { rating ->
        LazyColumn {
            item {
                Text(text = stringResource(R.string.marks_screen_class_rating_title), style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(6.dp))

                val uriHandler = LocalUriHandler.current
                val banner = buildAnnotatedString {
                    append("Currently, deanonymization feature is broken. See ")
                    pushStringAnnotation("issue", "https://github.com/x3lfyn/libremesh/issues/18")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append("issue#18")
                    }
                    pop()


                }
                ClickableText(text = banner, onClick = {
                    banner.getStringAnnotations("issue", it, it).firstOrNull()?.let { stringAnnotation ->
                        uriHandler.openUri(stringAnnotation.item)
                    }
                }, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(4.dp).fillMaxWidth())
            }
            items(rating) {
                ClassRatingItemCard(
                    item = it,
                    modifier = Modifier.padding(6.dp),
                    anonymous = state.anonymousRating
                )
            }
        }
    }
}