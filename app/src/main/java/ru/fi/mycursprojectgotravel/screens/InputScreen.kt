package ru.fi.mycursprojectgotravel.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.fi.mycursprojectgotravel.navigation.NavRoutes
import ru.fi.mycursprojectgotravel.ui.theme.myColor


@Composable
fun InputScreen(navHostController: NavHostController){
    Box{
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                "Вход",
                fontWeight = FontWeight.Bold,
                fontSize = 23.sp
            )
            OutlinedButton(onClick = { navHostController.navigate(route = NavRoutes.Authorization.route) },
                Modifier.padding(10.dp))
            {
                Text(
                    "Авторизация",
                    color = myColor
                )
            }
            OutlinedButton(onClick = { navHostController.navigate(route = NavRoutes.Registration.route) },)
            {
                Text(
                    "Регистрация",
                    color = myColor
                )
            }
            Text(
                "Пропустить",
                fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {navHostController.navigate(route = NavRoutes.Main.route)}
                    .padding(10.dp)
            )
        }
    }
}