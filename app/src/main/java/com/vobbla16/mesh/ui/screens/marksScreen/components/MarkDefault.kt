package com.vobbla16.mesh.ui.screens.marksScreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vobbla16.mesh.domain.model.marks.GradeType
import com.vobbla16.mesh.domain.model.marks.Mark
import com.vobbla16.mesh.domain.model.marks.MarkValue
import kotlinx.datetime.LocalDate

@Composable
fun MarkDefault(
    mark: MarkDefaultValue,
    modifier: Modifier = Modifier,
    size: MarkDefaultSize = MarkDefaultSize.Default
) {
    OutlinedCard(modifier) {
        Box(Modifier.height(size.size).widthIn(size.size, Dp.Infinity)) {
            Text(
                text = mark.value.toString(),
                modifier = Modifier.align(
                    Alignment.Center,
                ).padding(6.dp), style = MaterialTheme.typography.titleMedium
            )
            mark.weight?.let {
                Text(
                    text = it.toString(),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(4.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            if (mark.isPoint) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "can be changed",
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .scale(0.5f)
                        .offset((-3).dp, (-3).dp),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

enum class MarkDefaultSize(val size: Dp) {
    Default(48.dp),
    Small(38.dp)
}

data class MarkDefaultValue(
    val value: Number,
    val weight: Int?,
    val isPoint: Boolean
)

@Preview(
    showBackground = true,
    device = "spec:width=128px,height=128px,dpi=440"
)
@Composable
fun MarkDefaultPreview1() {
    val mark = Mark(
        comment = null,
        controlFormName = "Самостоятельная работа",
        date = LocalDate(2022, 12, 28),
        gradeType = GradeType.Five,
        isPoint = true,
        pointDate = null,
        topic = "Что-то там",
        value = MarkValue(4f, 80f),
        weight = 2,
        id = 0
    )
    MarkDefault(mark = mark.toMarkDefaultValue(), size = MarkDefaultSize.Small)
}

@Preview(
    showBackground = true,
    device = "spec:width=128px,height=128px,dpi=440"
)
@Composable
fun MarkDefaultPreview2() {
    val mark = Mark(
        comment = null,
        controlFormName = "Самостоятельная работа",
        date = LocalDate(2022, 12, 28),
        gradeType = GradeType.Five,
        isPoint = true,
        pointDate = LocalDate(2022, 12, 28),
        topic = "Что-то там",
        value = MarkValue(5f, 100f),
        weight = 2,
        id = 0
    )
    MarkDefault(mark = mark.toMarkDefaultValue())
}

@Preview(
    showBackground = true,
    device = "spec:width=128px,height=128px,dpi=440"
)
@Composable
fun MarkDefaultPreview3() {
    MarkDefault(mark = MarkDefaultValue(
        4.66, null, false
    ), size = MarkDefaultSize.Small
    )
}