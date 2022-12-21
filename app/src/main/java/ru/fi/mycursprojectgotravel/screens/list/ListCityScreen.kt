package ru.fi.mycursprojectgotravel.screens.list

import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import ru.fi.mycursprojectgotravel.R
import ru.fi.mycursprojectgotravel.getCodeForNews
import ru.fi.mycursprojectgotravel.model.City
import ru.fi.mycursprojectgotravel.navigation.NavRoutes
import ru.fi.mycursprojectgotravel.utils.SELECTED_CITY
import ru.fi.mycursprojectgotravel.utils.PRIMARY_COLOR
import ru.fi.mycursprojectgotravel.viewModel.CityViewModel
import ru.fi.mycursprojectgotravel.viewModel.CityViewModelFactory
import java.util.*

@Composable
fun ListCityScreen(navHostController: NavHostController){
    val context = LocalContext.current
    val fViewModel: CityViewModel =
        viewModel(factory = CityViewModelFactory(context.applicationContext as Application))


    var listCity : MutableList<City> by remember { mutableStateOf(mutableListOf()) }
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    fViewModel.getCollectionCity {
        listCity = it
    }

    Column(modifier = Modifier.padding(top = 55.dp, bottom = 55.dp)) {
        SearchView(state = textState)
        if(listCity.isEmpty()){
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
        }else {
            ListCity(listCity, textState, navHostController)
        }
    }
}


@Composable
private fun SearchView(state: MutableState<TextFieldValue>) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 5.dp)
            .border(border = BorderStroke(3.dp, color = PRIMARY_COLOR.value), shape = RoundedCornerShape(15.dp)),

        textStyle = TextStyle(fontSize = 18.sp),
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
                            TextFieldValue("")
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
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}


@Composable
private fun ListCity(
    listCity: MutableList<City>,
    state: MutableState<TextFieldValue>,
    navHostController: NavHostController
){
    var filteredCites: MutableList<City>
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        val searchedText = state.value.text
        filteredCites = if(searchedText.isEmpty()){
            listCity
        }else {
            val resultList: MutableList<City> = mutableListOf()
            var i = 0
            for(city in listCity){
                if(search(city.name, searchedText)
                ){
                    resultList.add(i,city)
                    ++i
                }
            }
            resultList
        }
        items(items = filteredCites){ item ->
            AttractionListItem(
                nameCity = item.name,
                image = item.image,
                onItemClick = {
                    SELECTED_CITY = item
                    navHostController.navigate(route = NavRoutes.InfoCity.route)
                }
            )
        }
    }
}

fun search(text : String, searchedText : String): Boolean{
    return text.lowercase(Locale.getDefault())
            .contains(searchedText.lowercase(Locale.getDefault()))
}


@Composable
private fun AttractionListItem(
    nameCity : String,
    image: String,
    onItemClick:(City) -> Unit
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable(onClick = {onItemClick(City())}),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp,
    ){
        Box(){
            Row(verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = rememberImagePainter(
                        data = image,
                        builder ={
                            crossfade(false)
                            placeholder(R.drawable.crocodile)
                        }

                    ),
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(5.dp)
                        .size(70.dp)
                        .clip(CircleShape)

                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(text = nameCity)
                }

            }
        }
    }
}
