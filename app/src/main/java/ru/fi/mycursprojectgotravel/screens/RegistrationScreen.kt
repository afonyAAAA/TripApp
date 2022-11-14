package ru.fi.mycursprojectgotravel.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.fi.mycursprojectgotravel.R
import ru.fi.mycursprojectgotravel.ui.theme.myColor

@Composable
fun RegistrationScreen(navHostController: NavHostController){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = stringResource(R.string.authorizaton),
            fontWeight = FontWeight.Bold,
            fontSize = 23.sp,
            modifier = Modifier.padding(10.dp)
        )
            TextField(
                value = "",
                onValueChange = {},
                label = { Text(text = stringResource(R.string.login))},
                placeholder = { Text(text = stringResource(R.string.PleaseLogin))},
                modifier = Modifier.padding(10.dp)
            )

            TextField(
                value = "",
                onValueChange = {},
                label = { Text(text = stringResource(R.string.password))},
                placeholder = { Text(text = stringResource(R.string.PleasePassword))},
                modifier = Modifier.padding(10.dp)
            )

            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(text = stringResource(R.string.PleaseRepeatPassword))},
                modifier = Modifier.padding(10.dp)
            )
            Button(
                onClick = {},
                modifier = Modifier
                    .width(150.dp)
                    .padding(10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = myColor, contentColor = Color.White)
            ) {
                Text(text = stringResource(R.string.confirm))
            }


    }
}