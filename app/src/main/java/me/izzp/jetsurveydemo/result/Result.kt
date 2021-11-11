package me.izzp.jetsurveydemo.result

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.izzp.jetsurveydemo.ui.theme.grey
import me.izzp.jetsurveydemo.ui.theme.mtColors
import me.izzp.jetsurveydemo.ui.theme.mtShapes
import me.izzp.jetsurveydemo.ui.theme.mtTypography


@Composable
fun ResultItem(
    question: String,
    result: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, mtColors.grey, mtShapes.medium)
            .padding(8.dp)
    ) {
        Text(
            text = question,
            style = mtTypography.h6,
        )
        Divider(Modifier.padding(0.dp, 6.dp))
        result()
    }
}

@Composable
fun ResultItemEmpty(question: String) {
    ResultItem(question) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = "empty",
                style = mtTypography.body1
            )
        }
    }
}