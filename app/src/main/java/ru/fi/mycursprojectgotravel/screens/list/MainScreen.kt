package ru.fi.mycursprojectgotravel.screens.list

import android.annotation.SuppressLint
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import kotlinx.coroutines.runBlocking
import ru.fi.mycursprojectgotravel.viewModel.MainViewModel
import ru.fi.mycursprojectgotravel.viewModel.MainViewModelFactory
import ru.fi.mycursprojectgotravel.R
import ru.fi.mycursprojectgotravel.model.Country
import ru.fi.mycursprojectgotravel.navigation.NavRoutes
import ru.fi.mycursprojectgotravel.utils.COUNTRY_LIST
import ru.fi.mycursprojectgotravel.utils.SELECTED_COUNTRY
import ru.fi.mycursprojectgotravel.utils.STATE_FAVORITE
import ru.fi.mycursprojectgotravel.utils.PRIMARY_COLOR
import java.util.*

lateinit var mViewModel: MainViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun MainScreen(navHostController: NavHostController){

        val context = LocalContext.current

        mViewModel =  viewModel(factory = MainViewModelFactory(context.applicationContext as Application))

        STATE_FAVORITE = remember { mutableStateOf(false) }

        var listCountry : MutableList<Country> by remember { mutableStateOf(mutableListOf()) }

        val textState = remember { mutableStateOf(TextFieldValue("")) }

        mViewModel.getCollectionCountry {
            listCountry = it
            COUNTRY_LIST = it
        }
        Column() {
            SearchView(textState)
            if(listCountry.isEmpty()){
               Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                   CircularProgressIndicator()
               }
            }else {
                ListCountry(listCountry, textState, navHostController)
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
            .border(border = BorderStroke(3.dp, color = PRIMARY_COLOR.value),
                shape = RoundedCornerShape(15.dp)),

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
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchViewPreview() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    SearchView(textState)
}

@Composable
private fun ListCountry(
    listCountry: MutableList<Country>,
    state: MutableState<TextFieldValue>,
    navHostController: NavHostController
){
    var filteredCountries: MutableList<Country>
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        val searchedText = state.value.text
        filteredCountries = if(searchedText.isEmpty()){
             listCountry
        }else {
            val resultList: MutableList<Country> = mutableListOf()
            var i = 0
            for(country in listCountry){
                if(country.nameCountry.lowercase(Locale.getDefault())
                        .contains(searchedText.lowercase(Locale.getDefault()))
                ){
                    resultList.add(i,country)
                    ++i
                }
            }
            resultList
        }
        items(items = filteredCountries){ item ->
            CountryListItem(
                nameCountry = item.nameCountry,
                image = item.imageCountry,
                onItemClick = {
                    mViewModel.getListElementFavorite( {
                        SELECTED_COUNTRY = item
                        for(element in it){
                            if(SELECTED_COUNTRY.id == element.id){
                               STATE_FAVORITE.value = true
                            }
                        }
                        navHostController.navigate(route = NavRoutes.InfoCountry.route)
                    },
                    {
                        SELECTED_COUNTRY = item
                        navHostController.navigate(route = NavRoutes.InfoCountry.route)
                    })
               }
            )
        }
    }
    
}

@Composable
private fun CountryListItem(
    nameCountry : String,
    image: String,
    onItemClick:(Country) -> Unit
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable(onClick = { onItemClick(Country()) }),
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
                    Text(text = nameCountry)
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    val navController = rememberNavController()

}

