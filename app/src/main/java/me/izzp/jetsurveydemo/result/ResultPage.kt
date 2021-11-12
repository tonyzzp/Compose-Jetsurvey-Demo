package me.izzp.jetsurveydemo.result

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import me.izzp.jetsurveydemo.FormViewModel
import me.izzp.jetsurveydemo.LocalSystemBarsInfo
import me.izzp.jetsurveydemo.QuestionType
import me.izzp.jetsurveydemo.ui.theme.mtColors
import me.izzp.jetsurveydemo.widget.CenterTitleAppBar
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ResultPage(
    vm: FormViewModel,
    onDone: () -> Unit,
) {
    val systemBarsInfo = LocalSystemBarsInfo.current
    Scaffold(
        topBar = {
            CenterTitleAppBar(
                title = { Text("Result") },
                backgroundColor = mtColors.surface,
                topPadding = systemBarsInfo.statusBarHeightDp
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            vm.questions.forEach { question ->
                val answer by vm.observeAnswer(question)
                if (answer == null) {
                    ResultItemEmpty(question.question)
                } else {
                    when (question.type) {
                        QuestionType.single -> {
                            when (answer) {
                                is Int -> {
                                    ResultSingle(question.question, answer as Int)
                                }
                                is String -> {
                                    ResultSingle(question.question, answer as String)
                                }
                                is Uri -> {
                                    ResultSingle(question.question, answer as Uri)
                                }
                            }
                        }
                        QuestionType.multi -> {
                            val list = answer as List<*>
                            if (list.isEmpty()) {
                                ResultItemEmpty(question.question)
                            } else {
                                when (list.first()) {
                                    is Int -> {
                                        ResultMulti(question.question, list as List<Int>)
                                    }
                                    is String -> {
                                        ResultMulti(question.question, list as List<String>)
                                    }
                                }
                            }
                        }
                        QuestionType.date -> {
                            val sdf = SimpleDateFormat("yyyy-MM-dd")
                            ResultSingle(question.question, sdf.format(answer as Date))
                        }
                        QuestionType.progress -> {
                            ResultSingle(question.question, (answer as Float).toString())
                        }
                        QuestionType.photo -> {
                            ResultSingle(question.question, answer as Uri)
                        }
                    }
                }
            }
            Button(
                onClick = onDone,
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("DONE")
            }
        }
    }
}