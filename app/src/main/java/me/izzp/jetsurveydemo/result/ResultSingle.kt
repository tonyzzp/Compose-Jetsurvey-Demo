package me.izzp.jetsurveydemo.result

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import me.izzp.jetsurveydemo.R
import me.izzp.jetsurveydemo.ui.theme.mtTypography


@Composable
fun ResultSingle(
    question: String,
    @DrawableRes result: Int,
) {
    ResultItem(question) {
        Image(
            painterResource(result),
            null,
            modifier = Modifier.size(150.dp)
        )
    }
}

@Composable
fun ResultSingle(
    question: String,
    result: Uri,
) {
    ResultItem(question) {
        Image(
            rememberImagePainter(result),
            null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(150.dp)
        )
    }
}

@Composable
fun ResultSingle(
    question: String,
    result: String,
) {
    ResultItem(question) {
        Text(
            text = result,
            style = mtTypography.body1,
        )
    }
}

@Composable
@Preview
private fun PreviewSingleText() {
    ResultSingle("what is the question", "this is the answer")
}

@Composable
@Preview
private fun PreviewSingleImage() {
    ResultSingle("what is the question", R.drawable.frag)
}