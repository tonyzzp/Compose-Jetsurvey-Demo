package me.izzp.jetsurveydemo

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel

class FormViewModel(
    val imageStore: ImageStore
) : ViewModel() {

    val questions = questionsData
    private val _answers = mutableStateMapOf<Question, Any?>()

    fun observeAnswer(question: Question) = derivedStateOf { _answers[question] }

    fun commitAnswer(question: Question, answer: Any) {
        _answers[question] = answer
    }

}