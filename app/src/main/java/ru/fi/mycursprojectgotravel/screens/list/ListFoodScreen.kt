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
import ru.fi.mycursprojectgotravel.model.Food
import ru.fi.mycursprojectgotravel.navigation.NavRoutes
import ru.fi.mycursprojectgotravel.utils.SELECTED_FOOD
import ru.fi.mycursprojectgotravel.utils.PRIMARY_COLOR
import ru.fi.mycursprojectgotravel.utils.Vegan
import ru.fi.mycursprojectgotravel.viewModel.FoodViewModel
import ru.fi.mycursprojectgotravel.viewModel.FoodViewModelFactory
import java.util.*

@Composable
fun ListFoodScreen(navHostController: NavHostController){

    val context = LocalContext.current
    val fViewModel: FoodViewModel =
        viewModel(factory = FoodViewModelFactory(context.applicationContext as Application))


    var listFood : MutableList<Food> by remember { mutableStateOf(mutableListOf()) }
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val checkedState : MutableState<Boolean> = remember { mutableStateOf(false) }


    fViewModel.getCollectionFood {
        listFood = it
    }

    Column(modifier = Modifier.padding(top = 55.dp, bottom = 55.dp)) {
        SearchView(state = textState)
        checkBox(state = checkedState)
        if(listFood.isEmpty()){
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
        }else {
            ListFood(listFood, textState, navHostController, checkedState)
        }
    }

}


@Composable
private fun checkBox(state: MutableState<Boolean>){
    Row() {
        Checkbox(
            checked = state.value,
            onCheckedChange = {state.value = it},
            modifier = Modifier.padding(5.dp)
        )
        Text(text = Vegan, fontSize = 15.sp, modifier =  Modifier.padding(top = 19.dp))
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
private fun ListFood(
    listFood: MutableList<Food>,
    state: MutableState<TextFieldValue>,
    navHostController: NavHostController,
    stateCheckBox: MutableState<Boolean>
){
    var filteredFood: MutableList<Food>
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        val searchedText = state.value.text
        val filter = stateCheckBox.value
        filteredFood = if(searchedText.isEmpty() && stateCheckBox.value == false){
            listFood
        }else {
            val resultList: MutableList<Food> = mutableListOf()
            var i = 0
            for(food in listFood){
                if(food.name.lowercase(Locale.getDefault())
                        .contains(searchedText.lowercase(Locale.getDefault()))
                    && stateCheckBox.value == false)
                {
                    resultList.add(i,food)
                    ++i
                }
                if(food.name.lowercase(Locale.getDefault())
                        .contains(searchedText.lowercase(Locale.getDefault()))
                    && stateCheckBox.value == true && stateCheckBox.value == food.vegetable)
                {
                    resultList.add(i,food)
                    ++i
                }
            }
            resultList
        }
        items(items = filteredFood){ item ->
            FoodListItem(
                nameFood = item.name,
                image = item.image,
                onItemClick = {
                    SELECTED_FOOD = item
                    navHostController.navigate(route = NavRoutes.InfoFood.route)
                }
            )
        }
    }
}

@Composable
private fun FoodListItem(
    nameFood : String,
    image: String,
    onItemClick:(Food) -> Unit
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable(onClick = { onItemClick(Food()) }),
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
                    Text(text = nameFood)
                }

            }
        }
    }
}
