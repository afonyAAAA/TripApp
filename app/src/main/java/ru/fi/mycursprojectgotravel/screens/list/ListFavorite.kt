package ru.fi.mycursprojectgotravel.screens.list

import android.app.Application
import androidx.compose.foundation.*
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import ru.fi.mycursprojectgotravel.R
import ru.fi.mycursprojectgotravel.model.Country
import ru.fi.mycursprojectgotravel.navigation.NavRoutes
import ru.fi.mycursprojectgotravel.ui.theme.myColor
import ru.fi.mycursprojectgotravel.utils.*
import ru.fi.mycursprojectgotravel.viewModel.FavoriteViewModel
import ru.fi.mycursprojectgotravel.viewModel.FavoriteViewModelFactory
import java.util.*

lateinit var selectedIndex: MutableState<Int>

@Composable
fun ListFavorite(navHostController: NavHostController){

    val context = LocalContext.current
    val fViewModel :FavoriteViewModel =
        viewModel(factory = FavoriteViewModelFactory(context.applicationContext as Application))

    val textState = remember { mutableStateOf(TextFieldValue("")) }
    selectedIndex = remember { mutableStateOf(0) }



    var listCountry : MutableList<Country> by remember { mutableStateOf(mutableListOf()) }
    fViewModel.getListElementFavorite {
        listCountry = it
    }

    Column() {
        Box(modifier = Modifier.padding(top = 55.dp)) {
            SearchView(state = textState)
        }
        if(listCountry.isEmpty()){
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
        }else {
            ListCountry(listCountry = listCountry, state = textState, navHostController = navHostController)
        }
    }





}

@Composable
fun MyDropdownMenu(){
    var expanded by remember { mutableStateOf(false) }
    val items = listOf(
        stringResource(R.string.Countries),
        stringResource(R.string.Food),
        stringResource(R.string.Cites),
        stringResource(R.string.AttractionText))
    Box(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.TopStart)) {
        Text(stringResource(R.string.Category))
        Text(items[selectedIndex.value],modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { expanded = true })
            .background(
                Color.Gray))
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    myColor)
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex.value = index
                    expanded = false
                }) {
                    Text(text = s)
                }
            }
        }
    }

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
