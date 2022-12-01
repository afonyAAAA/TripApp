package ru.fi.mycursprojectgotravel.screens

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.fi.mycursprojectgotravel.R
import ru.fi.mycursprojectgotravel.RegistrationViewModel
import ru.fi.mycursprojectgotravel.navigation.NavRoutes
import ru.fi.mycursprojectgotravel.ui.theme.MyCursProjectGoTravelTheme
import ru.fi.mycursprojectgotravel.ui.theme.myColor


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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

                Text(
                    text = stringResource(R.string.hello),
                    style = TextStyle(fontSize = MaterialTheme.typography.subtitle1.fontSize)
                )
                Text(
                    text = stringResource(R.string.HelloMessage),
                    style = TextStyle(fontSize = MaterialTheme.typography.subtitle1.fontSize)
                )

            Button(
                onClick = {
                    navHostController.navigate(route = NavRoutes.Input.route)
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