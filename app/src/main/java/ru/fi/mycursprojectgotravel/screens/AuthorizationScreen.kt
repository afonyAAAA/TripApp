package ru.fi.mycursprojectgotravel.screens

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ru.fi.mycursprojectgotravel.viewModel.AuthorizationViewModel
import ru.fi.mycursprojectgotravel.viewModel.AuthorizationViewModelFactory
import ru.fi.mycursprojectgotravel.R
import ru.fi.mycursprojectgotravel.navigation.NavRoutes
import ru.fi.mycursprojectgotravel.ui.theme.myColor
import ru.fi.mycursprojectgotravel.utils.*


@Composable
fun AuthorizationScreen(navHostController: NavHostController){

    val context = LocalContext.current
    val aViewModel: AuthorizationViewModel =
        viewModel(factory = AuthorizationViewModelFactory(context.applicationContext as Application))

    val passwordIsInvalid: String = PasswordExcept
    val emailExcept: String = emailExcept
    val passwordExcept: String = PasswordExcept2
    val otherExcept : String = otherExcept


    var login by remember { mutableStateOf("") }
    var password by rememberSaveable{ mutableStateOf("") }
    var passwordVisible by rememberSaveable{ mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }



    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = authorizaton,
            fontWeight = FontWeight.Bold,
            fontSize = 23.sp,
            modifier = Modifier.padding(10.dp)
        )

        OutlinedTextField(
            value = login,
            onValueChange = {login = it},
            label = { Text(text = LoginText )},
            placeholder = { Text(text = stringResource(R.string.PleaseLogin)) },
            modifier = Modifier.padding(10.dp),
            singleLine = true

        )

        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = { Text(text = PasswordText)},
            modifier = Modifier.padding(10.dp),
            singleLine = true,
            placeholder = { Text(text = PleasePassword)},
            visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if(passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, description)
                }
            },


        )

        Button(
            onClick = {
                LOGIN = login
                PASSWORD = password
                if(password.length >= 6){
                    aViewModel.authorization( {
                        navHostController.navigate(route = NavRoutes.Main.route)
                    },
                        {
                            when(it){
                                "The email address is badly formatted." -> {
                                    scope.launch{
                                        snackbarHostState.value.showSnackbar(emailExcept)
                                    }
                                }
                                "There is no user record corresponding to this identifier. The user may have been deleted." ->{
                                    scope.launch{
                                        snackbarHostState.value.showSnackbar(emailExcept)
                                    }
                                }
                                "The password is invalid or the user does not have a password."->{
                                    scope.launch{
                                        snackbarHostState.value.showSnackbar(passwordExcept)
                                    }
                                }
                                else -> {
                                    scope.launch{
                                        snackbarHostState.value.showSnackbar(otherExcept)
                                    }
                                }
                            }
                        }
                    )
                }else{
                    scope.launch{
                        snackbarHostState.value.showSnackbar(passwordIsInvalid)
                    }
                }
            },
            modifier = Modifier
                .width(150.dp)
                .padding(10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = myColor, contentColor = Color.White),
            enabled = login.isNotEmpty() && password.isNotEmpty()
        ) {
            Text(text = confirm)
        }
        Box(){
            SnackbarHost(snackbarHostState.value)
        }


    }
}