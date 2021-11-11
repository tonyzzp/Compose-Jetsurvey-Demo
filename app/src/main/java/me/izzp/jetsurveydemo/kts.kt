package me.izzp.jetsurveydemo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun rememberBoolean(init: Boolean = false) = remember { mutableStateOf(init) }

@Composable
fun rememberString(init: String = "") = remember { mutableStateOf(init) }

@Composable
fun rememberInt(init: Int = 0) = remember { mutableStateOf(init) }

@Composable
fun rememberFloat(init: Float = 0f) = remember { mutableStateOf(init) }

fun <T> MutableList<T>.addOrRemove(value: T) {
    if (!remove(value)) {
        add(value)
    }
}