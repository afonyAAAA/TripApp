package ru.fi.mycursprojectgotravel.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.fi.mycursprojectgotravel.navigation.NavRoutes
import ru.fi.mycursprojectgotravel.R
import ru.fi.mycursprojectgotravel.utils.*


@Composable
fun InputScreen(navHostController: NavHostController){
    Box{
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = SelectSign,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp
            )

            OutlinedButton(onClick = { navHostController.navigate(NavRoutes.Authorization.route) },
                border = BorderStroke(2.dp, PRIMARY_COLOR.value),
                modifier = Modifier.padding(10.dp))
            {
                Text(
                    text = authorizaton
                )
            }

            OutlinedButton(
                onClick = { navHostController.navigate(route = NavRoutes.Registration.route) },
                border = BorderStroke(2.dp, PRIMARY_COLOR.value)
            )
            {
                Text(
                    text = registration
                )
            }

            Text(
                text = Skip,
                fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable {
                        navHostController.navigate(route = NavRoutes.Main.route)
                    }
                    .padding(10.dp)
            )
        }
    }
}
