package me.izzp.jetsurveydemo.signin

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import kotlinx.coroutines.launch
import me.izzp.jetsurveydemo.*
import me.izzp.jetsurveydemo.R
import me.izzp.jetsurveydemo.ui.theme.mtColors
import me.izzp.jetsurveydemo.ui.theme.mtTypography
import me.izzp.jetsurveydemo.widget.CenterTitleAppBar
import me.izzp.jetsurveydemo.widget.JetTextField
import me.izzp.jetsurveydemo.widget.rememberTextFieldState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SigninPage(
    onBackClick: () -> Unit,
    onSiginClick: () -> Unit,
    onGuestClick: () -> Unit,
) {
    Surface(
        Modifier.fillMaxSize(),
    ) {
        val snackbarHostState = remember { SnackbarHostState() }
        Box(
            Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().navigationBarsPadding(),
            ) {
                CenterTitleAppBar(
                    title = { Text("SIGN IN") },
                    navigationIcon = {
                        IconButton(onBackClick) {
                            Icon(Icons.Default.ArrowBack, null)
                        }
                    },
                    topPadding = LocalSystemBarsInfo.current.statusBarHeightDp,
                    backgroundColor = mtColors.surface,
                )
                Body(
                    snackbarHostState = snackbarHostState,
                    onSiginClick = onSiginClick,
                    onGuestClick = onGuestClick,
                )
            }
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter).navigationBarsPadding()
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Body(
    snackbarHostState: SnackbarHostState,
    onSiginClick: () -> Unit,
    onGuestClick: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(20.dp))

        val email = rememberTextFieldState()
        val pwd = rememberTextFieldState()
        val keyboardController = LocalSoftwareKeyboardController.current
        JetTextField(
            state = email,
            onValueChange = {
                email.value = it
                email.error = errEmail(it)
            },
            modifier = Modifier.fillMaxWidth().padding(0.dp, 8.dp),
            label = "Email",
            imeAction = ImeAction.Next,
            onImeAction = {
                pwd.focusRequester.requestFocus()
            }
        )

        var visible by rememberBoolean()
        JetTextField(
            state = pwd,
            onValueChange = {
                pwd.value = it
                pwd.error = errPassword(it)
            },
            modifier = Modifier.fillMaxWidth().padding(0.dp, 8.dp),
            label = "Password",
            imeAction = ImeAction.Done,
            onImeAction = {
                pwd.focusRequester.freeFocus()
                keyboardController?.hide()
            },
            trailing = {
                IconButton(
                    onClick = {
                        visible = !visible
                    }
                ) {
                    androidx.compose.material.Icon(
                        painterResource(if (visible) R.drawable.ic_baseline_visibility_24 else R.drawable.ic_baseline_visibility_off_24),
                        null,
                    )
                }
            },
            visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation()
        )

        Button(
            onClick = onSiginClick,
            enabled = email.error == null && email.value.length > 0 && pwd.error == null && pwd.value.length > 0,
            modifier = Modifier.fillMaxWidth().padding(0.dp, 8.dp)
        ) {
            Text("Sign in")
        }
        Spacer(Modifier.height(20.dp))
        TextButton(
            onClick = {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("unavailable", null, SnackbarDuration.Short)
                }
            },
            modifier = Modifier.fillMaxWidth().padding(0.dp, 8.dp)
        ) {
            Text("FORGOT PASSWORD?")
        }
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = "or",
                style = mtTypography.caption,
                modifier = Modifier.padding(0.dp, 8.dp)
            )
        }
        OutlinedButton(
            onClick = onGuestClick,
            modifier = Modifier.fillMaxWidth().padding(0.dp, 8.dp)
        ) {
            Text("SIGN IN AS GUEST")
        }
    }
}
