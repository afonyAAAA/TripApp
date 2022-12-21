package ru.fi.mycursprojectgotravel

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Man
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.fi.mycursprojectgotravel.navigation.BottomNavItem
import ru.fi.mycursprojectgotravel.navigation.NavRoutes
import ru.fi.mycursprojectgotravel.navigation.TripNavHost
import ru.fi.mycursprojectgotravel.ui.theme.GoTravelTheme
import ru.fi.mycursprojectgotravel.ui.theme.Theme
import ru.fi.mycursprojectgotravel.ui.theme.myColor
import ru.fi.mycursprojectgotravel.utils.*
import java.util.*


class MainActivity : ComponentActivity() {
    val user =  FirebaseAuth.getInstance().currentUser
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
           setContent {

               PRIMARY_COLOR = remember{ mutableStateOf(myColor)}

               if(isOnline(this)){
                   UI_THEME = remember { mutableStateOf(Theme.LIGHT)}
                   TEXT_SIZE = remember{ mutableStateOf(19.sp) }
                   LOCALIZATION_USER = Locale.getDefault().language
                   getLocaleUser()
                   if(LOCALIZATION_USER == "en"){
                       translateAllStrings()
                   }
                   GoTravelTheme(theme = UI_THEME.value) {
                       BottomBarAnimationApp(user)
                   }

               }else{
                   val openDialog = remember { mutableStateOf(true) }
                  AlertDialogInternet(openDialog)
                   if(!openDialog.value){
                      finish()
                   }
               }
           }
    }
}

fun isOnline(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetwork
    return netInfo != null
}

@Composable
fun AlertDialogInternet(openDialog: MutableState<Boolean>){
    AlertDialog(
        onDismissRequest = {
           openDialog.value = true
        },
        title = { Text(text = NoInternet)},
        text = { Text(NoInternetDescription)},
        buttons = {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = {openDialog.value = false},
                    colors = ButtonDefaults.buttonColors(backgroundColor = PRIMARY_COLOR.value)
                ) {
                    Text("OK", fontSize = 22.sp)
                }
            }

        })
}

@Composable
fun BottomBarAnimationApp(user: FirebaseUser?) {

    val scaffoldState = rememberScaffoldState(DrawerState(DrawerValue.Closed))
    var startDestination: MutableState<String>

    if(user == null){
        ACTIVE_USER = remember { mutableStateOf(null) }
        DiSPLAY_NAME = remember {mutableStateOf("")}
        startDestination = rememberSaveable{ mutableStateOf(NavRoutes.Start.route) }

    }else{
        ACTIVE_USER = remember{ mutableStateOf(user.uid)}
        DiSPLAY_NAME = remember {mutableStateOf(user.displayName)}
        startDestination = rememberSaveable{ mutableStateOf(NavRoutes.Main.route) }
    }


    val scope = rememberCoroutineScope()

    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }

    val topBarState = rememberSaveable { (mutableStateOf(false)) }

        val navController = rememberNavController()

        val navBackStackEntry by navController.currentBackStackEntryAsState()

        when (navBackStackEntry?.destination?.route) {
            "main_screen"->{
                bottomBarState.value = false
                topBarState.value = false
            }
            "foodList_screen" -> {
                bottomBarState.value = true
                topBarState.value = true
            }
            "attractionScreen_screen" -> {
                bottomBarState.value = true
                topBarState.value = true
            }
            "infoCountry_screen" -> {
                bottomBarState.value = true
                topBarState.value = true
            }
            "infoCulture_screen" -> {
                bottomBarState.value = true
                topBarState.value = true
            }
            "cityList_screen" ->{
                bottomBarState.value = true
                topBarState.value = true
            }
            "infoCity_screen"->{
                bottomBarState.value = true
                topBarState.value = true
            }
            "newsList_screen"->{
                bottomBarState.value = true
                topBarState.value = true
            }
        }

        com.google.accompanist.insets.ui.Scaffold(
            scaffoldState = scaffoldState,
            drawerContent = {
                Text(
                    buildAnnotatedString{
                        withStyle(style = SpanStyle(PRIMARY_COLOR.value)){
                            append("Go")
                        }
                        append("Travel")
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    textAlign = TextAlign.Center


                )
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {

                    if(ACTIVE_USER.value != null){
                        IconButton(onClick = {
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                            navController.navigate(NavRoutes.FavoriteList.route)
                                             },
                            modifier = Modifier.padding(5.dp)) {
                            Row {
                                Icon(Icons.Filled.Favorite, contentDescription = "Избранное", tint = PRIMARY_COLOR.value)
                                Text(text = Favorite, fontSize = 20.sp, modifier = Modifier.padding(start = 5.dp))
                            }
                        }
                    }

                    IconButton(onClick = {
                        scope.launch {
                            delay(timeMillis = 250)
                            scaffoldState.drawerState.close()
                        }
                        navController.navigate(NavRoutes.SettingsScreen.route)
                                         }, modifier = Modifier.padding(5.dp)) {
                        Row {
                            Icon(Icons.Filled.Settings, contentDescription = "Настройки", tint = PRIMARY_COLOR.value)
                            Text(text = Settings, fontSize = 20.sp, modifier = Modifier.padding(start = 5.dp))
                        }
                    }

                    if(ACTIVE_USER.value == null){
                        IconButtonAnonymous(navController, scaffoldState, scope)
                    }else IconButtonUser(navController, scaffoldState, scope, startDestination)
                }
            },
            bottomBar = {
                BottomNavigationBar(
                    navController = navController,
                    bottomBarState = bottomBarState,
                    startDestination = startDestination
                )
            },
            topBar = {
               TopBar(
                    navController = navController,
                    topBarState = topBarState
                )
            },
            content = {
                TripNavHost(bottomBarState, topBarState, navController, startDestination)
            }

        )
    }

@Composable
fun IconButtonAnonymous(navController: NavHostController, scaffoldState: ScaffoldState, scope: CoroutineScope){
    IconButton(onClick =
    {
        scope.launch {
            delay(timeMillis = 250)
            scaffoldState.drawerState.close()
        }
        navController.navigate(NavRoutes.Input.route)
    }, modifier = Modifier.padding(10.dp)) {
        Row {
            Icon(Icons.Filled.Man, contentDescription = "Логин", tint = PRIMARY_COLOR.value)
            Text(text = "Вход", fontSize = 20.sp, modifier = Modifier.padding(start = 5.dp))
        }
    }
}


@Composable
fun IconButtonUser(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    startDestination: MutableState<String>,
){
    Row {
        Icon(Icons.Filled.VerifiedUser, contentDescription = "Логин", tint = PRIMARY_COLOR.value)
        Text(text = DiSPLAY_NAME.value.toString(), fontSize = 20.sp, modifier = Modifier.padding(start = 5.dp))
    }
    Column {
        OutlinedButton(
            onClick = {
                scope.launch {
                    delay(timeMillis = 250)
                    scaffoldState.drawerState.close()
                }
                FirebaseAuth.getInstance().signOut()
                ACTIVE_USER.value = null
                startDestination.value = NavRoutes.Input.route
                navController.navigate(NavRoutes.Input.route)
            },
            modifier = Modifier.padding(5.dp)
        ) {
            Text(Exit)
        }
    }
}

@Composable
fun TopBar(navController: NavHostController, topBarState: MutableState<Boolean>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val title: String = when (navBackStackEntry?.destination?.route ?: Countries) {
        "intoCountry_screen" -> InfoCountry
        "foodList_screen" -> Citchen
        "attractionScreen_screen" -> AttractionText
        "infoCulture_screen" -> CultureText
        "infoCity_screen" -> InfoCity
        "cityList_screen" -> Cites
        "newsList_screen" -> News
        "favoriteList_screen" -> Favorite
        "settings_screen" -> Settings
        else -> " "
    }
    AnimatedVisibility(
        visible = topBarState.value,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            TopAppBar(
                title = { Text(text = title) },
            )
        }
    )
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    startDestination: MutableState<String>,
) {

    val items = listOf(
        BottomNavItem.Culture,
        BottomNavItem.FoodList,
        BottomNavItem.AttractionList,
        BottomNavItem.CityList,
        BottomNavItem.NewsList
    )
    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                if(navBackStackEntry?.destination?.route == NavRoutes.Main.route){
                    startDestination.value = NavRoutes.Main.route
                }


                items.forEach { item ->
                    BottomNavigationItem(
                        icon = { Icon(rememberImagePainter(data = item.icon), contentDescription = item.title) },
                        label = { Text(text = item.title, fontSize = 10.sp)},
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.White.copy(0.4f),
                        alwaysShowLabel = true,
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(NavRoutes.InfoCountry.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    )
}

