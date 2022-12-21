package ru.fi.mycursprojectgotravel.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.dfl.newsapi.enums.Language
import ru.fi.mycursprojectgotravel.R
import ru.fi.mycursprojectgotravel.model.Country
import ru.fi.mycursprojectgotravel.utils.*


@Composable
fun CultureScreen(navHostController: NavHostController){

    val activeCountry: MutableState<Country> = remember { mutableStateOf(SELECTED_COUNTRY) }

    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .verticalScroll(scrollState)) {
        Box(modifier = Modifier.padding(top = 55.dp)){
            ImageCulture(text = activeCountry.value.imageCountryCulture)
        }
        Box(modifier = Modifier.padding(start = 5.dp)){
            DescriptionText(text = activeCountry.value.generalDescriptionCulture)
        }
        Box(modifier = Modifier.padding(start = 5.dp)){
            HabitsAndCustomsDescriptionText(text = activeCountry.value.habitsAndCustomsDescription)
        }
        Box(modifier = Modifier.padding(start = 5.dp)){
            ArchitectureDescription(text = activeCountry.value.architectureDescription)
        }
        Box(modifier = Modifier.padding(start = 5.dp,bottom = 55.dp)){
            MusicDescriptionText(text = activeCountry.value.musicDescription)
        }

    }
}

@Composable
fun ImageCulture(text : String){
    Image(
        painter = rememberImagePainter(
            data = text,
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
            .horizontalScroll(rememberScrollState())
    )

}

@Composable
fun DescriptionText(text : String){
    Text(
        text = text,
        fontSize = TEXT_SIZE.value,
        textAlign = TextAlign.Left,
        fontFamily = FontFamily.SansSerif
    )
}


@Composable
fun HabitsAndCustomsDescriptionText(text: String){
    Column() {
        Text(
            text = HabitsAndCustoms,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 30.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .padding(start = 5.dp, top = 10.dp, bottom = 10.dp)
        )
        Text(
            text = text,
            fontSize = TEXT_SIZE.value,
            textAlign = TextAlign.Justify,
            fontFamily = FontFamily.SansSerif
        )

    }
}

@Composable
fun ArchitectureDescription(text: String){
    Column() {
        Text(
            text = Architecture,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 30.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .padding(start = 5.dp, top = 10.dp, bottom = 10.dp)
        )
        Text(
            text = text,
            fontSize = TEXT_SIZE.value,
            textAlign = TextAlign.Justify,
            fontFamily = FontFamily.SansSerif
        )

    }
}

@Composable
fun MusicDescriptionText(text: String){
    Column() {
        Text(
            text = Music,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 30.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .padding(start = 5.dp, top = 10.dp, bottom = 10.dp)
        )
        Text(
            text = text,
            fontSize = TEXT_SIZE.value,
            textAlign = TextAlign.Justify,
            fontFamily = FontFamily.SansSerif
        )

    }
}

@Composable
fun LanguageDescriptionText(text: String){
    Column() {
        Text(
            text = stringResource(R.string.Music),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 30.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .padding(start = 5.dp, top = 10.dp, bottom = 10.dp)
        )
        Text(
            text = text,
            fontSize = TEXT_SIZE.value,
            textAlign = TextAlign.Justify,
            fontFamily = FontFamily.SansSerif
        )

    }
}

