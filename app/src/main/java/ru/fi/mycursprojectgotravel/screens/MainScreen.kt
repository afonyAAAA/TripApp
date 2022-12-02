package ru.fi.mycursprojectgotravel.screens

import android.app.Application
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.fi.mycursprojectgotravel.MainViewModel
import ru.fi.mycursprojectgotravel.MainViewModelFactory
import ru.fi.mycursprojectgotravel.R
import ru.fi.mycursprojectgotravel.model.Country
import ru.fi.mycursprojectgotravel.ui.theme.myColor
import kotlin.concurrent.thread


@Composable
fun MainScreen(navHostController: NavHostController){




    val context = LocalContext.current
    val mViewModel: MainViewModel =
        viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
    var listCountry : MutableList<Country> = mutableListOf()

    runBlocking {
        launch {
            mViewModel.getCollectionCountry {
                listCountry = it
            }
        }
    }

    val textState = remember { mutableStateOf(TextFieldValue("")) }



        SearchView(textState)
        ListCountry(list = listCountry)




}

@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 5.dp)
            .border(border = BorderStroke(3.dp, color = myColor), shape = RoundedCornerShape(15.dp)),

        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.Black,
            trailingIconColor = Color.White,
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    SearchView(textState)
}

@Composable
private fun ListCountry(list: MutableList<Country>){
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        itemsIndexed(list){_, item ->
            CountryListItem(nameCountry = item.nameCountry)
        }
    }
    
}


@Composable
private fun CountryListItem(nameCountry : String){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp,
        ){
        Box(){
            Row(verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(5.dp)
                        .size(70.dp)
                        .clip(CircleShape)

                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(text = nameCountry)
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()

}

