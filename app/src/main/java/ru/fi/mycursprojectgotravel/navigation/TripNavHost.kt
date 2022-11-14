package ru.fi.mycursprojectgotravel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.fi.mycursprojectgotravel.screens.*

sealed class NavRoutes(val route: String){
    object Start: NavRoutes("start_screen")
    object Main: NavRoutes("main_screen")
    object InfoCountry: NavRoutes(" intoCountry_screen")
    object Authorization: NavRoutes("authorization_screen")
    object Registration: NavRoutes("registration_screen")
    object Input: NavRoutes("input_screen")

}

@Composable
fun TripNavHost(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoutes.Start.route){
        composable(NavRoutes.Start.route){ StartScreen(navHostController = navController) }
        composable(NavRoutes.Main.route){ MainScreen(navHostController = navController) }
        composable(NavRoutes.InfoCountry.route){ InfoCountryScreen(navHostController = navController) }
        composable(NavRoutes.Authorization.route){ AuthorizationScreen(navHostController = navController) }
        composable(NavRoutes.Registration.route){ RegistrationScreen(navHostController = navController) }
        composable(NavRoutes.Input.route){ InputScreen(navHostController = navController) }
    }
}