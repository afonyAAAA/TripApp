package ru.fi.mycursprojectgotravel.screens

import android.app.Application
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ru.fi.mycursprojectgotravel.MainViewModel
import ru.fi.mycursprojectgotravel.MainViewModelFactory
import ru.fi.mycursprojectgotravel.navigation.NavRoutes
import ru.fi.mycursprojectgotravel.ui.theme.myColor
import ru.fi.mycursprojectgotravel.R
import ru.fi.mycursprojectgotravel.RegistrationViewModel
import ru.fi.mycursprojectgotravel.utils.list


@Composable
fun InputScreen(navHostController: NavHostController){
    val context = LocalContext.current
    val mViewModel: MainViewModel =
        viewModel(factory = MainViewModelFactory(context.applicationContext as Application))

    Box{
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = stringResource(R.string.SelectSign),
                fontWeight = FontWeight.Light,
                fontSize = 16.sp
            )

            OutlinedButton(onClick = { navHostController.navigate(NavRoutes.Authorization.route) },
                border = BorderStroke(2.dp, myColor),
                modifier = Modifier.padding(10.dp))
            {
                Text(
                    text = stringResource(R.string.authorizaton),
                    color = myColor
                )
            }

            OutlinedButton(
                onClick = { navHostController.navigate(route = NavRoutes.Registration.route) },
                border = BorderStroke(2.dp, myColor)
            )
            {
                Text(
                    text = stringResource(R.string.registration),
                    color = myColor
                )
            }

            Text(
                text = stringResource(R.string.Skip),
                fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable {
                       mViewModel.getCollectionCountry(
                            {
                                list = it
                                Log.d("DATA", "отличные данные чувак${list.get(0).nameCountry}")
                            },
                            {

                            }
                        )
                        navHostController.navigate(route = NavRoutes.Main.route)
                    }
                    .padding(10.dp)
            )
        }
    }
}
