package ru.fi.mycursprojectgotravel

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.fi.mycursprojectgotravel.navigation.TripNavHost
import ru.fi.mycursprojectgotravel.ui.theme.myColor


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val rViewModel: RegistrationViewModel =
                viewModel(factory = RegistrationViewModelFactory(context.applicationContext as Application))
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                        Text(text = "GoTravel")
                    },
                    backgroundColor = myColor,
                    contentColor = Color.White,
                    elevation = 12.dp
                    )
            },
                content = {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        TripNavHost(rViewModel)
                    }
                }
            )


        }
    }
}

