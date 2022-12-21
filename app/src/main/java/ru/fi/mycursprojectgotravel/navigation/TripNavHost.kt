package ru.fi.mycursprojectgotravel.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.fi.mycursprojectgotravel.R
import ru.fi.mycursprojectgotravel.screens.*
import ru.fi.mycursprojectgotravel.screens.list.*
import ru.fi.mycursprojectgotravel.utils.*

sealed class NavRoutes(val route: String){
    object Start: NavRoutes("start_screen")
    object Main: NavRoutes("main_screen")
    object Authorization: NavRoutes("authorization_screen")
    object Registration: NavRoutes("registration_screen")
    object Input: NavRoutes("input_screen")
    object InfoFood: NavRoutes("infoFood_screen")
    object InfoCulture: NavRoutes("infoCulture_screen")
    object InfoCity: NavRoutes("infoCity_screen")
    object InfoAttraction: NavRoutes("infoAttraction_screen")
    object InfoCountry: NavRoutes("intoCountry_screen")
    object FoodList: NavRoutes("foodList_screen")
    object CityList: NavRoutes("cityList_screen")
    object AttractionList: NavRoutes("attractionScreen_screen")
    object NewsList : NavRoutes("newsList_screen")
    object FavoriteList : NavRoutes("favoriteList_screen")
    object SettingsScreen : NavRoutes("settings_screen")

}

sealed class BottomNavItem(val route: String, val title: String, val icon : Int ){
    object Culture : BottomNavItem(
        route = NavRoutes.InfoCulture.route,
        title = CultureText,
        icon = R.drawable.crocodile
    )
    object FoodList : BottomNavItem(
        route = NavRoutes.FoodList.route,
        title = FoodText,
        icon = R.drawable.crocodile
    )
    object AttractionList : BottomNavItem(
        route = NavRoutes.AttractionList.route,
        title = Places,
        icon = R.drawable.crocodile
    )
    object CityList : BottomNavItem(
        route = NavRoutes.CityList.route,
        title = Cites,
        icon = R.drawable.crocodile
    )
    object NewsList : BottomNavItem(
        route = NavRoutes.NewsList.route,
        title = News,
        icon = R.drawable.crocodile
    )
}
@Composable
fun TripNavHost(
    bottomBarState: MutableState<Boolean>,
    topBarState: MutableState<Boolean>,
    navController: NavHostController,
    startDestination: MutableState<String>
) {
    NavHost(navController = navController, startDestination = startDestination.value){
        composable(NavRoutes.Start.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = false
                topBarState.value = false
            }
            StartScreen(navHostController = navController)
        }
        composable(NavRoutes.Main.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = false
                topBarState.value = false
            }
            MainScreen(navHostController = navController)
        }
        composable(NavRoutes.InfoCountry.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = true
                topBarState.value = true
            }
            InfoCountryScreen(navHostController = navController)
        }
        composable(NavRoutes.Authorization.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = false
                topBarState.value = false
            }
            AuthorizationScreen(navHostController = navController)
        }
        composable(NavRoutes.Registration.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = false
                topBarState.value = false
            }
            RegistrationScreen(navHostController = navController)
        }
        composable(NavRoutes.Input.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = false
                topBarState.value = false
            }
            startDestination.value = NavRoutes.Input.route
            InputScreen(navHostController = navController)
        }
        composable(NavRoutes.InfoFood.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = false
                topBarState.value = false
            }
            FoodScreen(navHostController = navController)
        }
        //composable(NavRoutes.Map.route){ Map(navHostController = navController)}
        composable(NavRoutes.InfoCity.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = false
                topBarState.value = false
            }
            CityScreen(navHostController = navController)
        }
        composable(NavRoutes.FavoriteList.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = false
                topBarState.value = true
            }
            ListFavorite(navHostController = navController)
        }
        composable(NavRoutes.InfoAttraction.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = false
                topBarState.value = false
            }
            AttractionScreen(navHostController = navController)
        }
        composable(NavRoutes.NewsList.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = true
                topBarState.value = true
            }
            NewsScreen()
        }
        composable(NavRoutes.FoodList.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = true
                topBarState.value = true
            }
            ListFoodScreen(navHostController = navController)
        }
        composable(NavRoutes.AttractionList.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = true
                topBarState.value = true
            }
            ListAttractionScreen(navHostController = navController)
        }
        composable(NavRoutes.CityList.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = true
                topBarState.value = true
            }
            ListCityScreen(navHostController = navController)
        }
        composable(NavRoutes.InfoCulture.route){
            LaunchedEffect(Unit) {
            bottomBarState.value = true
            topBarState.value = true
        }
            CultureScreen(navHostController = navController)
        }
        composable(NavRoutes.SettingsScreen.route){
            LaunchedEffect(Unit) {
                bottomBarState.value = false
                topBarState.value = true
            }
           SettingsScreen(navHostController = navController)
        }
    }
}

