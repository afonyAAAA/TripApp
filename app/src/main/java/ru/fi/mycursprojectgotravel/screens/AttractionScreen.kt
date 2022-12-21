package ru.fi.mycursprojectgotravel.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import ru.fi.mycursprojectgotravel.R
import ru.fi.mycursprojectgotravel.model.Attraction
import ru.fi.mycursprojectgotravel.model.City
import ru.fi.mycursprojectgotravel.utils.PRIMARY_COLOR
import ru.fi.mycursprojectgotravel.utils.SELECTED_ATTRACTION
import ru.fi.mycursprojectgotravel.utils.SELECTED_CITY
import ru.fi.mycursprojectgotravel.utils.TEXT_SIZE

@Composable
fun AttractionScreen(navHostController: NavHostController){
    val activeCity: MutableState<Attraction> = remember { mutableStateOf(SELECTED_ATTRACTION) }

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        Box(modifier = Modifier.padding(top = 20.dp, start = 5.dp, bottom = 19.dp)){
            NameAttraction(activeCity)
        }

        ImageAttraction(activeCity)

        Box(modifier = Modifier.padding(start = 5.dp, top = 5.dp,bottom = 55.dp)){
            DescriptionText(activeCity)
        }

    }
}

@Composable
private fun ImageAttraction(activeCity: MutableState<Attraction>){
    Image(
        painter = rememberImagePainter(
            data = activeCity.value.image,
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
private fun DescriptionText(activeCity: MutableState<Attraction>){
    Text(
        text = activeCity.value.description,
        fontSize = TEXT_SIZE.value,
        textAlign = TextAlign.Left,
        fontFamily = FontFamily.SansSerif
    )
}

@Composable
private fun NameAttraction(activeCity: MutableState<Attraction>){
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
                    text = activeCity.value.name,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 30.sp
                )
            }
        }
    }
}
