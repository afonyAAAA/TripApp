package ru.fi.mycursprojectgotravel.utils

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import kotlinx.coroutines.runBlocking
import me.bush.translator.Language
import ru.fi.mycursprojectgotravel.database.firebase.AppFireBaseRepository
import ru.fi.mycursprojectgotravel.database.firebase.DatabaseRepository
import ru.fi.mycursprojectgotravel.model.Attraction
import ru.fi.mycursprojectgotravel.model.City
import ru.fi.mycursprojectgotravel.model.Country
import ru.fi.mycursprojectgotravel.model.Food
import ru.fi.mycursprojectgotravel.translateGoogle
import ru.fi.mycursprojectgotravel.ui.theme.Theme
import com.dfl.newsapi.enums.Country as EnumsCountry


var LoginText = "Логин"
var PasswordText = "Пароль"
var Hello = "Привет!"
var PleaseLogin = "Введите логин"
var PleasePassword = "Введите пароль"
var PleaseRepeatPassword = "Повторите пароль"
var HelloMessage = "Это приложение для путешествия."
var Sign = "Вход"
var Skip = "Пропустить"
var registration = "Регистрация"
var authorizaton = "Авторизация"
var confirm = "Потвердить"
var SelectSign = "Выберите способ входа в приложение:"
var CultureText = "Культура"
var FoodText = "Кухня"
var CityText = "Города"
var AttractionText = "Достопримечательности"
var HabitsAndCustoms = "Привычки и обычаи"
var Architecture = "Архитектура"
var Music = "Музыка"
var Cites = "Города"
var Exit = "Выйти"
var Countries = "Страны"
var Food = "Блюда"
var News = "Новости"
var Places = "Места"
var Green = "Зелёная"
var Dark = "Темная"
var Light = "Светлая"
var Vegan = "Вегатарианское"
var ColorsThemeApp = "Цветовая тема приложения:"
var Settings = "Настройки"
var Favorite = "Избранное"
var InfoSizeText = "Данная настройка изменяет размер шрифта текста на таких окнах, где находится большое количество текста для чтения"
var PasswordExcept = "Длина пароля должна быть больше 5 символов"
var exceptAutorization = "Неправильный логин или пароль"
var emailExcept = "Проверьте корректность введенного логина"
var PasswordExcept2 = "Пароль неправильный или такого аккаунта с таким паролем не существует"
var otherExcept = "Авторизация сейчас невозможна. Попробуйте повторить позже."
var UserIsExist = "Аккаунт с таким логином уже существует"
var PasswordIsNotValid2 = "Проверьте правильность повтора пароля"
var NoInternet = "Отсутствует интернет соединение!"
var NoInternetDescription = "Для работы приложения требуется подключение к интернету"
var Start = "Начнём!"
var InfoCountry = "Информация о стране"
var Citchen = "Кухня"
var InfoCity = "Информация о городе"
var Small = "Маленький"
var Average = "Средний"
var Big = "Большой"
var sizeText = "Размер текста: "



fun translateAllStrings() = runBlocking{
    LoginText = translateGoogle(LoginText)
    Start = translateGoogle(Start)
    PasswordText = translateGoogle(PasswordText)
    Hello = translateGoogle(Hello)
    PleaseLogin = translateGoogle(PleaseLogin)
    PleasePassword = translateGoogle(PleasePassword)
    PleaseRepeatPassword = translateGoogle(PleaseRepeatPassword)
    HelloMessage = translateGoogle(HelloMessage)
    Sign = translateGoogle(Sign)
    Skip = translateGoogle(Skip)
    registration = translateGoogle(registration)
    authorizaton = translateGoogle(authorizaton)
    confirm = translateGoogle(confirm)
    SelectSign = translateGoogle(SelectSign)
    CultureText = translateGoogle(CultureText)
    FoodText = translateGoogle(FoodText)
    CityText = translateGoogle(CityText)
    AttractionText = translateGoogle(AttractionText)
    HabitsAndCustoms = translateGoogle(HabitsAndCustoms)
    Architecture = translateGoogle(Architecture)
    Music = translateGoogle(Music)
    Cites = translateGoogle(Cites)
    Exit = translateGoogle(Exit)
    Countries = translateGoogle(Countries)
    Food = translateGoogle(Food)
    News = translateGoogle(News)
    Places = translateGoogle(Places)
    Green = translateGoogle(Green)
    Dark = translateGoogle(Dark)
    Light = translateGoogle(Light)
    Vegan = translateGoogle(Vegan)
    ColorsThemeApp = translateGoogle(ColorsThemeApp)
    InfoSizeText = translateGoogle(InfoSizeText)
    PasswordExcept = translateGoogle(PasswordExcept)
    exceptAutorization = translateGoogle(exceptAutorization)
    emailExcept = translateGoogle(emailExcept)
    PasswordExcept2 = translateGoogle(PasswordExcept2)
    otherExcept = translateGoogle(otherExcept)
    UserIsExist = translateGoogle(UserIsExist)
    PasswordIsNotValid2 = translateGoogle(PasswordIsNotValid2)
    NoInternet = translateGoogle(NoInternet)
    NoInternetDescription = translateGoogle(NoInternetDescription)
    Settings = translateGoogle(Settings)
    Favorite = translateGoogle(Favorite)
    Citchen = translateGoogle(Citchen)
    Start = translateGoogle(Start)
    InfoCity = translateGoogle(InfoCity)
    InfoCountry = translateGoogle(InfoCountry)
    Small = translateGoogle(Small)
    Average = translateGoogle(Average)
    Big = translateGoogle(Big)
    sizeText = translateGoogle(sizeText)
}


lateinit var DiSPLAY_NAME : MutableState<String?>
lateinit var TEXT_SIZE : MutableState<TextUnit>
lateinit var UI_THEME : MutableState<Theme>
lateinit var PRIMARY_COLOR : MutableState<Color>

lateinit var VALUE_LOCALE : Enum<Language>
lateinit var VALUE_LOCALE_NEWS : Enum<EnumsCountry>
lateinit var LOCALIZATION_USER: String
lateinit var ACTIVE_USER : MutableState<String?>

lateinit var LOGIN: String
lateinit var PASSWORD: String

lateinit var SELECTED_COUNTRY: Country
lateinit var SELECTED_FOOD: Food
lateinit var SELECTED_CITY: City
lateinit var SELECTED_ATTRACTION: Attraction

lateinit var COUNTRY_LIST: MutableList<Country>
lateinit var STATE_FAVORITE : MutableState<Boolean>

val REPOSITORY : DatabaseRepository = AppFireBaseRepository()

