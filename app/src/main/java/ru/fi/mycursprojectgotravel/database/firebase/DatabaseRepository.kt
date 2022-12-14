package ru.fi.mycursprojectgotravel.database.firebase

import ru.fi.mycursprojectgotravel.model.*

interface DatabaseRepository {

    fun readCountry(OnSuccess: (list:MutableList<Country>) -> Unit, onFail: (String) -> Unit)

    fun readFood(OnSuccess: (list:MutableList<Food>) -> Unit, onFail: (String) -> Unit)

    fun readCity(OnSuccess: (list:MutableList<City>) -> Unit, onFail: (String) -> Unit)

    fun readFavorite(  onSuccess: (MutableList<Country>) -> Unit, onFail: (String) -> Unit, idFavorite : String)

    fun readAttraction(onSuccess: (list: MutableList<Attraction>) -> Unit, onFail: (String) -> Unit)

    fun addData()

    fun addIdFavorite(onSuccess: (String) -> Unit)

    fun addFavorite(idElementFavorite: String, idFavorite : String,  onFail: () -> Unit, onSuccess: () -> Unit)

    fun checkIdFavorite(onSuccess: (Boolean) -> Unit)

    fun addUser(idUser : String)

    fun registration(onSuccess: () -> Unit, onFail: (String) -> Unit)

    fun authoruzation(onSuccess: () -> Unit, onFail: (String) -> Unit)

    fun getFavoritesUser(onSuccess: (MutableList<CountryFavorites>) -> Unit, idfavorite : String)

    fun getIdFavoriteUser(onSuccess: (String) -> Unit, onFail: (String) -> Unit)

    fun deleteFavorite(idElementFavorite: String, idFavorite : String)

}