package me.izzp.jetsurveydemo.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import me.izzp.jetsurveydemo.ui.theme.mtTypography

@Composable
private fun ResultMulti(
    question: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    ResultItem(question) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            content()
        }
    }
}

@JvmName("ResultMultiString")
@Composable
fun ResultMulti(
    question: String,
    result: List<String>,
) {
    ResultMulti(question) {
        result.forEach {
            Text(
                text = it,
                style = mtTypography.body1,
                modifier = Modifier.padding(0.dp, 4.dp)
            )
        }
    }
}

@JvmName("ResultMultiImage")
@Composable
fun ResultMulti(
    question: String,
    result: List<Int>,
) {
    ResultMulti(question) {
        result.forEach {
            Image(
                painterResource(it),
                null,
                modifier = Modifier.size(150.dp)
            )
        }
    }
}