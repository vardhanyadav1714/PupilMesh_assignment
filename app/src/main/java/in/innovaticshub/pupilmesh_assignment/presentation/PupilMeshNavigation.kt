package `in`.innovaticshub.pupilmesh_assignment.presentation

import MangaDetailScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import `in`.innovaticshub.pupilmesh_assignment.home_screen.presentation.HomeScreen
import `in`.innovaticshub.pupilmesh_assignment.login_screen.presentation.LoginScreen

@Composable
fun PupilMeshAppNavigation() {
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = PupilMeshScreens.SPLASH_SCREEN.name) {
        composable(PupilMeshScreens.SPLASH_SCREEN.name) {
            SplashScreen(navController)
        }
        composable(PupilMeshScreens.LOGIN_SCREEN.name) {
            LoginScreen(navController)
        }

        composable(PupilMeshScreens.HOME_SCREEN.name) {
            HomeScreen(navController)
        }

        composable("${PupilMeshScreens.MANGA_DESCRIPTION_SCREEN.name}/{mangaId}") { backStackEntry ->
            val mangaId = backStackEntry.arguments?.getString("mangaId")
            MangaDetailScreen(mangaId)
        }
    }
}

