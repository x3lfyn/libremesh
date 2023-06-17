package com.vobbla16.mesh.ui.screens.marksScreen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vobbla16.mesh.R

@Composable
fun CheckMark(mark: Number, bgColor: Color, type: CheckMarkType, modifier: Modifier = Modifier) {
    Card(colors = CardDefaults.cardColors(containerColor = bgColor), modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(2.dp)) {
            when (type) {
                CheckMarkType.ZeroCheck -> {}
                CheckMarkType.SingleCheck -> {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "check icon"
                    )
                }

                CheckMarkType.DoubleCheck -> {
                    Icon(
                        painter = painterResource(R.drawable.done_all),
                        contentDescription = "double check icon"
                    )
                }
            }

            Text(
                text = mark.toString(),
                modifier = Modifier.padding(
                    if (type == CheckMarkType.ZeroCheck) 12.dp else 4.dp,
                    2.dp
                )
            )
        }
    }
}

enum class CheckMarkType {
    ZeroCheck,
    SingleCheck,
    DoubleCheck,
}

@Preview(showBackground = true)
@Composable
fun CheckMarkPreview1() {
    CheckMark(
        mark = 4,
        bgColor = MaterialTheme.colorScheme.secondaryContainer,
        type = CheckMarkType.ZeroCheck,
        modifier = Modifier.padding(8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun CheckMarkPreview2() {
    CheckMark(
        mark = 5,
        bgColor = MaterialTheme.colorScheme.secondaryContainer,
        type = CheckMarkType.SingleCheck,
        modifier = Modifier.padding(8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun CheckMarkPreview3() {
    CheckMark(
        mark = 5,
        bgColor = MaterialTheme.colorScheme.tertiaryContainer,
        type = CheckMarkType.DoubleCheck,
        modifier = Modifier.padding(8.dp)
    )
}