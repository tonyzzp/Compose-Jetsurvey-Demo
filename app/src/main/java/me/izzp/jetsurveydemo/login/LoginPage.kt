package me.izzp.jetsurveydemo.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import me.izzp.jetsurveydemo.R
import me.izzp.jetsurveydemo.errEmail
import me.izzp.jetsurveydemo.ui.theme.mtTypography
import me.izzp.jetsurveydemo.widget.JetTextField
import me.izzp.jetsurveydemo.widget.rememberTextFieldState

@OptIn(ExperimentalComposeUiApi::class, ExperimentalPermissionsApi::class)
@Composable
fun LoginPage(
    onSignin: () -> Unit,
    onGuest: () -> Unit,
) {
    rememberSystemUiController().setSystemBarsColor(Color.Transparent, !isSystemInDarkTheme())
    Surface(
        Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painterResource(if (isSystemInDarkTheme()) R.drawable.ic_logo_dark else R.drawable.ic_logo_light),
                null,
                modifier = Modifier.padding(0.dp, 12.dp),
            )
            Text(
                text = "Better surveys with Jetpack Compose",
                style = mtTypography.subtitle1,
            )
            Spacer(Modifier.weight(1f))

            Text(
                text = "Sign in or create an account",
                style = mtTypography.subtitle1,
                color = LocalContentColor.current.copy(0.7f),
            )

            val keyboardController = LocalSoftwareKeyboardController.current
            val textFieldState = rememberTextFieldState()
            JetTextField(
                state = textFieldState,
                onValueChange = {
                    textFieldState.value = it
                    textFieldState.error = errEmail(it)
                },
                modifier = Modifier.fillMaxWidth().padding(0.dp, 8.dp),
                label = "Email",
                onImeAction = {
                    keyboardController?.hide()
                    textFieldState.focusRequester.freeFocus()
                }
            )

            Button(
                onClick = onSignin,
                modifier = Modifier.fillMaxWidth().padding(0.dp, 8.dp).height(48.dp),
                enabled = textFieldState.value.isNotEmpty() && textFieldState.error == null,
            ) {
                Text("CONTINUE")
            }
            Text(
                text = "or",
                style = mtTypography.body2,
                modifier = Modifier.padding(0.dp, 8.dp)
            )
            OutlinedButton(
                onClick = onGuest,
                modifier = Modifier.fillMaxWidth().padding(0.dp, 8.dp).height(48.dp),
            ) {
                Text("SIGN AS GUEST")
            }
        }
    }
}