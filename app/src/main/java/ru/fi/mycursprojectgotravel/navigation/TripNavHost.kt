package ru.fi.mycursprojectgotravel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.fi.mycursprojectgotravel.screens.InfoCountry
import ru.fi.mycursprojectgotravel.screens.Main
import ru.fi.mycursprojectgotravel.screens.Start

sealed class NavRoutes(val route: String){
    object Start: NavRoutes("start_screen")
    object Main: NavRoutes("main_screen")
    object InfoCountry: NavRoutes(" intoCountry_screen")
    object InfoFood: NavRoutes("infoFood_screen")

}

@Composable
fun TripNavHost(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoutes.Start.route){
        composable(NavRoutes.Start.route){ Start(navHostController = navController) }
        composable(NavRoutes.Main.route){ Main(navHostController = navController) }
        composable(NavRoutes.InfoCountry.route){ InfoCountry(navHostController = navController) }
    }
}