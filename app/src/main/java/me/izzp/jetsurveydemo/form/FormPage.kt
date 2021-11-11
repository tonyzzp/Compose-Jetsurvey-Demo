package me.izzp.jetsurveydemo.form

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import me.izzp.jetsurveydemo.FormViewModel
import me.izzp.jetsurveydemo.LocalSystemBarsInfo
import me.izzp.jetsurveydemo.questionsData
import me.izzp.jetsurveydemo.rememberInt
import me.izzp.jetsurveydemo.ui.theme.mtColors
import me.izzp.jetsurveydemo.widget.CenterTitleAppBar

@Composable
fun FormPage(
    vm: FormViewModel,
    onCloseClick: () -> Unit,
    onDone: () -> Unit,
) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize().navigationBarsPadding(),
        ) {
            val questions = vm.questions
            var index by rememberInt()
            CenterTitleAppBar(
                title = { Text("${index + 1} of ${questions.size}") },
                actions = {
                    IconButton(onCloseClick) {
                        androidx.compose.material.Icon(Icons.Default.Close, null)
                    }
                },
                topPadding = LocalSystemBarsInfo.current.statusBarHeightDp,
                backgroundColor = mtColors.surface,
            )
            val target = (index + 1) / questions.size.toFloat()
            val progress by animateFloatAsState(target, tween(300, 0, LinearEasing))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(0.dp, 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
            QuestionBox(
                vm = vm,
                questions[index],
                value = vm.observeAnswer(questions[index]).value,
                callback = {
                    vm.commitAnswer(questions[index], it)
                },
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
            )
            Row(
                Modifier.fillMaxWidth().padding(8.dp, 16.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        index--
                    },
                    enabled = index > 0,
                    modifier = Modifier.weight(1f).height(56.dp),
                ) {
                    Text("PREVIOUS")
                }
                Spacer(Modifier.width(8.dp))
                OutlinedButton(
                    onClick = {
                        if (index < questionsData.size - 1) {
                            index++
                        } else {
                            onDone()
                        }
                    },
                    enabled = true,
                    modifier = Modifier.weight(1f).height(56.dp),
                ) {
                    Text(if (index == questionsData.size - 1) "DONE" else "NEXT")
                }
            }
        }
    }
}