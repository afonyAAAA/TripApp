package ru.fi.mycursprojectgotravel

import com.dfl.newsapi.enums.Country
import me.bush.translator.Language
import me.bush.translator.Translator
import ru.fi.mycursprojectgotravel.utils.LOCALIZATION_USER
import ru.fi.mycursprojectgotravel.utils.SELECTED_COUNTRY
import ru.fi.mycursprojectgotravel.utils.VALUE_LOCALE
import ru.fi.mycursprojectgotravel.utils.VALUE_LOCALE_NEWS

val translator = Translator()

suspend fun translateGoogle(text: String) : String {
    val translation =
        translator.translate(text, VALUE_LOCALE as Language, Language.AUTO)
    return translation.translatedText
}

fun getLocaleUser(){
    enumValues<Language>().forEach { language ->
        if(LOCALIZATION_USER == language.code){
            VALUE_LOCALE = language
        }
    }
}

fun getCodeForNews(){
    enumValues<Country>().forEach { country ->
        if(SELECTED_COUNTRY.code  == country.value){
            VALUE_LOCALE_NEWS = country
        }
    }
}

