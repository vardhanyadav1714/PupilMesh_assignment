package `in`.innovaticshub.pupilmesh_assignment.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import `in`.innovaticshub.pupilmesh_assignment.login_screen.presentation.LoginState
import `in`.innovaticshub.pupilmesh_assignment.login_screen.presentation.LoginViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val viewModel: LoginViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        delay(2000)

        viewModel.loginState.collect { state ->
            when (state) {
                is LoginState.Authenticated -> {
                     navController.navigate(PupilMeshScreens.HOME_SCREEN.name) {
                        popUpTo(PupilMeshScreens.SPLASH_SCREEN.name) { inclusive = true }
                    }
                }
                is LoginState.Unauthenticated,
                is LoginState.Error,
                LoginState.Loading -> {
                     navController.navigate(PupilMeshScreens.LOGIN_SCREEN.name) {
                        popUpTo(PupilMeshScreens.SPLASH_SCREEN.name) { inclusive = true }
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

    }
}