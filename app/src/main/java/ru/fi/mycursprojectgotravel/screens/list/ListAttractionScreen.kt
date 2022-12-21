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
import ru.fi.mycursprojectgotravel.model.Attraction
import ru.fi.mycursprojectgotravel.model.City
import ru.fi.mycursprojectgotravel.navigation.NavRoutes
import ru.fi.mycursprojectgotravel.utils.PRIMARY_COLOR
import ru.fi.mycursprojectgotravel.utils.SELECTED_ATTRACTION
import ru.fi.mycursprojectgotravel.utils.SELECTED_CITY
import ru.fi.mycursprojectgotravel.viewModel.AttractionViewModel
import ru.fi.mycursprojectgotravel.viewModel.AttractionViewModelFactory
import java.util.*

@Composable
fun ListAttractionScreen(navHostController: NavHostController){
    val context = LocalContext.current
    val atViewModel: AttractionViewModel =
        viewModel(factory = AttractionViewModelFactory(context.applicationContext as Application))

    var listAttraction : MutableList<Attraction> by remember { mutableStateOf(mutableListOf()) }
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    atViewModel.getCollectionAttraction {
        listAttraction = it
    }

    Column(modifier = Modifier.padding(top = 55.dp, bottom = 55.dp)) {
        SearchView(state = textState)
        if(listAttraction.isEmpty()){
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
        }else {
            ListAttraction(listAttraction, textState, navHostController)
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
private fun ListAttraction(
    listAttraction: MutableList<Attraction>,
    state: MutableState<TextFieldValue>,
    navHostController: NavHostController
){
    var filteredAttraction: MutableList<Attraction>
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        val searchedText = state.value.text
        filteredAttraction = if(searchedText.isEmpty()){
            listAttraction
        }else {
            val resultList: MutableList<Attraction> = mutableListOf()
            var i = 0
            for(attraction in listAttraction){
                if(attraction.name.lowercase(Locale.getDefault())
                        .contains(searchedText.lowercase(Locale.getDefault()))
                ){
                    resultList.add(i,attraction)
                    ++i
                }
            }
            resultList
        }
        items(items = filteredAttraction){ item ->
            AttractionListItem(
                nameAttraction = item.name,
                image = item.image,
                onItemClick = {
                    SELECTED_ATTRACTION = item
                    navHostController.navigate(route = NavRoutes.InfoAttraction.route)
                }
            )
        }
    }
}


@Composable
private fun AttractionListItem(
    nameAttraction : String,
    image: String,
    onItemClick:(Attraction) -> Unit
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable(onClick = {onItemClick(Attraction())}),
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
                    Text(text = nameAttraction)
                }

            }
        }
    }
}