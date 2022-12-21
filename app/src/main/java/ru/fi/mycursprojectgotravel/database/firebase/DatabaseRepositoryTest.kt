package ru.fi.mycursprojectgotravel.database.firebase

import ru.fi.mycursprojectgotravel.model.*

interface DatabaseRepositoryTest {

    fun readCountry(OnSuccess: (list:MutableList<Country>) -> Unit, onFail: (String) -> Unit)

    fun readFood(OnSuccess: (list:MutableList<Food>) -> Unit, onFail: (String) -> Unit)

    fun readCity(OnSuccess: (list:MutableList<City>) -> Unit, onFail: (String) -> Unit)

    fun readFavorite( onSuccess: (MutableList<Country>) -> Unit, onFail: (String) -> Unit, idFavorite : String)

    fun addData()

    fun addIdFavorite(onSuccess: (String) -> Unit)

    fun addFavorite(idElementFavorite: String, idFavorite : String)  : Boolean

    fun checkIdFavorite(onSuccess: (Boolean) -> Unit)

    fun addUser(idUser : String)

    fun registration(login : String, password : String) : Boolean

    fun authorization(login : String, password : String) : Boolean

    fun getFavoritesUser(onSuccess: (MutableList<CountryFavorites>) -> Unit, idfavorite : String)

    fun getIdFavoriteUser(onSuccess: (String) -> Unit, onFail: (String) -> Unit)

    fun deleteFavorite(idElementFavorite: String, idFavorite : String) : Boolean

}