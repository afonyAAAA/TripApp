package ru.fi.mycursprojectgotravel.screens

import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import ru.fi.mycursprojectgotravel.R
import ru.fi.mycursprojectgotravel.model.Country
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.fi.mycursprojectgotravel.utils.*
import ru.fi.mycursprojectgotravel.viewModel.CountryViewModel
import ru.fi.mycursprojectgotravel.viewModel.CountryViewModelFactory

lateinit var cViewModel : CountryViewModel

@Composable
fun InfoCountryScreen(navHostController: NavHostController){

    val context = LocalContext.current
    cViewModel=
        viewModel(factory = CountryViewModelFactory(context.applicationContext as Application))

    enumValues<com.dfl.newsapi.enums.Country>().forEach { country ->
        if(SELECTED_COUNTRY.code == country.value){
            VALUE_LOCALE_NEWS = country
        }
    }

    val activeCountry: MutableState<Country> = remember {mutableStateOf(SELECTED_COUNTRY)}
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Box(modifier = Modifier.padding(top = 70.dp, start = 5.dp, bottom = 19.dp)){
            Row() {
                NameCountry(activeCountry)
                if(ACTIVE_USER.value != null){
                    ToggleButtonFavorite(checked = STATE_FAVORITE)
                }
            }
        }
        Box{
            ImageCountry(activeCountry)
        }
        Box(modifier = Modifier.padding(start = 5.dp, top = 5.dp, bottom = 55.dp)){
            DescriptionText(activeCountry)
        }
    }
}


@Composable
private fun ToggleButtonFavorite(checked: MutableState<Boolean>){
    IconToggleButton(checked = checked.value,
        onCheckedChange = {
            checked.value = it

        if(checked.value) {
            cViewModel.addFavorite(SELECTED_COUNTRY.id, {}, {})
        }else
            cViewModel.deleteFavorite(SELECTED_COUNTRY.id)

    }) {
        Icon(
            Icons.Outlined.Favorite,
            contentDescription = "Избранное",
            tint = if(checked.value){
                Color.Red
            } else{
                Color.Black
            },
            modifier = Modifier.padding(start = 5.dp) )
    }
}

@Composable
private fun ImageCountry(activeCountry: MutableState<Country>){
    Image(
        painter = rememberImagePainter(
            data = activeCountry.value.imageCountryDescription,
            builder ={
                crossfade(false)
                placeholder(R.drawable.crocodile)
            }
        ),
        contentDescription = "image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )

}

@Composable
private fun DescriptionText(activeCountry: MutableState<Country>){
    Text(
        text = activeCountry.value.generalDescription,
        fontSize = TEXT_SIZE.value,
        textAlign = TextAlign.Left,
        fontFamily = FontFamily.SansSerif
    )
}

@Composable
private fun NameCountry(activeCountry: MutableState<Country>){
    Card(
        modifier = Modifier
            .height(50.dp),
        shape = RoundedCornerShape(13.dp),
        border = BorderStroke(4.dp, PRIMARY_COLOR.value)
    ){
        Box(){
            Column(
                modifier = Modifier.padding(start = 15.dp, top = 5.5.dp, end = 15.dp),
            ) {
                Text(
                    text = activeCountry.value.nameCountry,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 30.sp
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    //NameCountry(activeCountry = "Страна" )
}