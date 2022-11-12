package ru.fi.mycursprojectgotravel.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.fi.mycursprojectgotravel.R
import ru.fi.mycursprojectgotravel.navigation.NavRoutes
import ru.fi.mycursprojectgotravel.ui.theme.MyCursProjectGoTravelTheme
import ru.fi.mycursprojectgotravel.ui.theme.myColor


@Composable
fun StartScreen(navHostController: NavHostController){
    Scaffold(modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(horizontalArrangement = Arrangement.Center){
                Text(
                    "Привет! Это приложение про путешествия!",
                    style = TextStyle(fontSize = MaterialTheme.typography.subtitle1.fontSize)
                )
            }
            Button(
                onClick = {
                    navHostController.navigate(route = NavRoutes.Main.route)
                },
                modifier = Modifier
                    .width(150.dp)
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = myColor, contentColor = Color.White)
            ) {
                Text("Начнём!")
            }
            }
        }
    }


@Preview(showBackground = true)
@Composable
fun prevStartScreen(){
    MyCursProjectGoTravelTheme() {
        StartScreen(navHostController = rememberNavController())
    }
}