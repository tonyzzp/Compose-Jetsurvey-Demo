package me.izzp.jetsurveydemo

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.izzp.jetsurveydemo.form.FormPage
import me.izzp.jetsurveydemo.login.LoginPage
import me.izzp.jetsurveydemo.result.ResultPage
import me.izzp.jetsurveydemo.signin.SigninPage
import me.izzp.jetsurveydemo.ui.theme.JetTheme


private class Factory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass != FormViewModel::class.java) {
            throw  NotImplementedError("support FormViewModel only")
        }
        return FormViewModel(ImageStore(context)) as T
    }
}

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val vm by viewModels<FormViewModel> { Factory(applicationContext) }
        setContent {
            JetTheme {
                Gate(vm)
            }
        }
    }
}

private fun navigateTo(
    navController: NavController,
    route: String,
    popUpToFirstPage: Boolean = false
) {
    navController.navigate(route) {
        if (popUpToFirstPage) {
            popUpTo("login")
        }
    }
}

@Composable
private fun Gate(vm: FormViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login",
    ) {
        composable("login") {
            LoginPage(
                onSignin = {
                    navigateTo(navController, "signin")
                },
                onGuest = {
                    navigateTo(navController, "form")
                },
            )
        }
        composable("signin") {
            SigninPage(
                onSiginClick = {
                    navigateTo(navController, "form", true)
                },
                onGuestClick = {
                    navigateTo(navController, "form", true)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable("form") {
            FormPage(
                vm = vm,
                onCloseClick = {
                    navController.popBackStack("login", false)
                },
                onDone = {
                    navigateTo(navController, "result", true)
                }
            )
        }
        composable("result") {
            ResultPage(
                vm = vm,
                onDone = {
                    navController.popBackStack("login", false)
                },
            )
        }
    }
}