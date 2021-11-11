package me.izzp.jetsurveydemo.widget

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import me.izzp.jetsurveydemo.ui.theme.mtColors

class TextFieldState internal constructor() {
    var error by mutableStateOf<String?>(null)
    var value by mutableStateOf("")
    val focusRequester = FocusRequester()
}

@Composable
fun rememberTextFieldState() = remember { TextFieldState() }

@Composable
fun JetTextField(
    state: TextFieldState,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    label: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLength: Int = 0,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {},
    trailing: (@Composable () -> Unit)? = null,
) {
    val colors = TextFieldDefaults.outlinedTextFieldColors(
        backgroundColor = Color.Transparent,
        focusedBorderColor = mtColors.primary,
        unfocusedBorderColor = mtColors.onSurface.copy(0.7f),
    )
    val labelCompose: (@Composable () -> Unit)? = if (label != null) {
        { Text(label) }
    } else {
        null
    }
    val placeholderCompose: (@Composable () -> Unit)? = if (label != null) {
        { Text(label) }
    } else {
        null
    }

    OutlinedTextField(
        value = state.value,
        onValueChange = {
            var s = it.trim().replace("\n", "").replace("\r\n", "")
            if (maxLength > 0 && s.length > maxLength) {
                s = s.substring(0, maxLength)
            }
            onValueChange(s)
        },
        modifier = modifier.focusRequester(focusRequester = state.focusRequester),
        textStyle = textStyle,
        label = labelCompose,
        placeholder = placeholderCompose,
        isError = state.error != null,
        visualTransformation = visualTransformation,
        singleLine = true,
        colors = colors,
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        keyboardActions = KeyboardActions(onAny = { onImeAction() }),
        trailingIcon = trailing,
    )
}