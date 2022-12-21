package ru.fi.mycursprojectgotravel.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.fi.mycursprojectgotravel.R
import ru.fi.mycursprojectgotravel.ui.theme.Theme
import ru.fi.mycursprojectgotravel.utils.*
import ru.fi.mycursprojectgotravel.utils.TEXT_SIZE
import ru.fi.mycursprojectgotravel.utils.UI_THEME

@Composable
fun SettingsScreen(navHostController: NavHostController){

    var progressTemp by remember { mutableStateOf(1f) }
    val progress = remember { mutableStateOf(progressTemp) }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 60.dp)) {
        FieldChangeColorTheme()
        FieldChangeSizeText(progress.value) {
          progress.value = it
        }
        InfoSizeText()
    }
}

@Composable
fun FieldChangeColorTheme(){
    Text(ColorsThemeApp,
        fontSize = 20.sp,
        fontFamily = FontFamily.SansSerif,
        modifier = Modifier.padding(start = 5.dp))

    val stateButtonBlack : MutableState<Boolean> = remember{ mutableStateOf(false)}
    val stateButtonWhite : MutableState<Boolean> = remember{ mutableStateOf(false)}
    val stateButtonArabic : MutableState<Boolean> = remember{ mutableStateOf(false)}

    when(UI_THEME.value){
        Theme.DARK -> {
            stateButtonBlack.value = false
            stateButtonArabic.value = true
            stateButtonWhite.value = true

        }
        Theme.ARABIC ->{
            stateButtonBlack.value = true
            stateButtonArabic.value = false
            stateButtonWhite.value = true
        }
        Theme.LIGHT ->{
            stateButtonBlack.value = true
            stateButtonArabic.value = true
            stateButtonWhite.value = false
        }
    }


    Row(horizontalArrangement = Arrangement.SpaceAround,modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)){
        OutlinedButton(
            onClick = {UI_THEME.value = Theme.LIGHT},
            enabled = stateButtonWhite.value,
            modifier = Modifier
                .height(50.dp),
            shape = RoundedCornerShape(15.dp))

        {
            Text(Light)
        }
        OutlinedButton(
            onClick = {UI_THEME.value = Theme.DARK},
            enabled = stateButtonBlack.value,
            modifier = Modifier
                .height(50.dp),

            shape = RoundedCornerShape(15.dp))
        {
            Text(Dark)
        }
        OutlinedButton(
            onClick = {UI_THEME.value = Theme.ARABIC},
            enabled = stateButtonArabic.value,
            modifier = Modifier
                .height(50.dp),

            shape = RoundedCornerShape(15.dp))
        {
            Text(Green)
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun FieldChangeSizeText(progress: Float, onSeek: (progress: Float) -> Unit){

    val sliderPosition = remember{ mutableStateOf(progress) }
    val tempSliderPosition = remember { mutableStateOf(progress) }
    var tierText by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val isDragged = interactionSource.collectIsDraggedAsState()

    when(sliderPosition.value){
        0f -> {
            tierText = Small
            TEXT_SIZE.value = 14.sp
        }
        1f -> {
            tierText = Average
            TEXT_SIZE.value = 19.sp
        }
        2f -> {
            tierText = Big
            TEXT_SIZE.value = 24.sp
        }
    }
    Text( "${sizeText + tierText}",
        fontSize = 20.sp,
        fontFamily = FontFamily.SansSerif,
        modifier = Modifier.padding(start = 5.dp))
    Slider(value = if(isDragged.value) tempSliderPosition.value else sliderPosition.value,
        valueRange = 0f..2f,
        steps = 1,
        onValueChange = { progress ->
            sliderPosition.value = progress
            tempSliderPosition.value = progress
        },
        onValueChangeFinished = {
          sliderPosition.value = tempSliderPosition.value
            onSeek(tempSliderPosition.value)
                        },
        interactionSource = interactionSource
    )
}

@Composable
fun InfoSizeText(){
    Text(text = InfoSizeText,
        fontSize = TEXT_SIZE.value,
        modifier = Modifier.padding(start = 5.dp))
}

@Composable
fun ChangeSizeText(progress: Float){

    val sliderPosition = remember{ mutableStateOf(progress) }
    val tempSliderPosition = remember { mutableStateOf(progress) }
    var tierText by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val isDragged = interactionSource.collectIsDraggedAsState()

    when(sliderPosition.value){
        0f -> {
            tierText = Small
            TEXT_SIZE.value = 14.sp
        }
        1f -> {
            tierText = Average
            TEXT_SIZE.value = 19.sp
        }
        2f -> {
            tierText = Big
            TEXT_SIZE.value = 24.sp
        }
    }
    Text( "${sizeText + tierText}",
        fontSize = 20.sp,
        fontFamily = FontFamily.SansSerif,
        modifier = Modifier.padding(start = 5.dp))
    Slider(value = if(isDragged.value) tempSliderPosition.value else sliderPosition.value,
        valueRange = 0f..2f,
        steps = 1,
        onValueChange = { progress ->
            sliderPosition.value = progress
            tempSliderPosition.value = progress
        },
        onValueChangeFinished = {
            sliderPosition.value = tempSliderPosition.value
        },
        interactionSource = interactionSource
    )
}