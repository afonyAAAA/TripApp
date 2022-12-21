package ru.fi.mycursprojectgotravel.screens

import android.app.Application
import android.widget.Toast
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ru.fi.mycursprojectgotravel.R
import ru.fi.mycursprojectgotravel.viewModel.RegistrationViewModel
import ru.fi.mycursprojectgotravel.viewModel.RegistrationViewModelFactory
import ru.fi.mycursprojectgotravel.navigation.NavRoutes
import ru.fi.mycursprojectgotravel.ui.theme.myColor
import ru.fi.mycursprojectgotravel.utils.*

@Composable
fun RegistrationScreen(navHostController: NavHostController){

    val context = LocalContext.current
    val rViewModel: RegistrationViewModel =
        viewModel(factory = RegistrationViewModelFactory(context.applicationContext as Application))

    var login by remember { mutableStateOf("")}
    var password by rememberSaveable{mutableStateOf("")}
    var passwordVisible by rememberSaveable{ mutableStateOf(false)}
    var repeatPassword by rememberSaveable{ mutableStateOf("")}

    val passwordIsNotValid = PasswordIsNotValid2
    val userIsExist:String = UserIsExist
    val emailExcept: String = emailExcept
    val otherExcept : String = otherExcept
    val passwordIsInvalid: String = PasswordExcept
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top


    ) {
        Text(
            text = registration,
            fontWeight = FontWeight.Bold,
            fontSize = 23.sp,
            modifier = Modifier.padding(10.dp)
        )
        OutlinedTextField(
            value = login,
            onValueChange = {login = it},
            label = { Text(text = LoginText)},
            placeholder = { Text(text = stringResource(R.string.PleaseLogin))},
            modifier = Modifier.padding(5.dp),
            isError = login.isEmpty(),
            singleLine = true
        )

        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = { Text(text = PasswordText)},
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
            modifier = Modifier.padding(10.dp),
            isError = password.isEmpty(),

        )

        OutlinedTextField(
            value = repeatPassword,
            onValueChange = {repeatPassword = it},
            placeholder = { Text(text = PleaseRepeatPassword)},
            singleLine = true,
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
            modifier = Modifier.padding(10.dp),
            isError = repeatPassword.isEmpty(),
        )

        Button(
            onClick = {
                if(password == repeatPassword){
                    if(password.length > 5){
                        LOGIN = login
                        PASSWORD = password
                        rViewModel.registration({
                            navHostController.navigate(route = NavRoutes.Authorization.route)
                        },
                            {
                                when (it) {
                                    "The email address is badly formatted." -> {
                                        scope.launch {
                                            snackbarHostState.value.showSnackbar(emailExcept)
                                        }
                                    }
                                    "The email address is already in use by another account."-> {
                                        scope.launch {
                                            snackbarHostState.value.showSnackbar(userIsExist)
                                        }
                                    }
                                    else -> {
                                        scope.launch {
                                            snackbarHostState.value.showSnackbar(it)
                                        }
                                    }
                                }
                            })
                    }else{
                        scope.launch {
                            snackbarHostState.value.showSnackbar(passwordIsInvalid)
                        }
                    }

                }else{
                    scope.launch {
                        snackbarHostState.value.showSnackbar(passwordIsNotValid)
                    }
                }
            },
            modifier = Modifier
                .width(150.dp)
                .padding(10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = PRIMARY_COLOR.value, contentColor = Color.White),
            enabled = login.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()

        ) {
            Text(text = confirm)
        }
        Box(){
            SnackbarHost(snackbarHostState.value)
        }

    }
}



