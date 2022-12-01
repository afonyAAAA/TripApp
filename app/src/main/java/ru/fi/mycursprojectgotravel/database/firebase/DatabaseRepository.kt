package ru.fi.mycursprojectgotravel.database.firebase

import ru.fi.mycursprojectgotravel.model.Country

interface DatabaseRepository {

    suspend fun create(country: Country, onSuccess: () -> Unit)

    suspend fun update(country: Country, onSuccess: () -> Unit)

    suspend fun delete(country: Country, onSuccess: () -> Unit)

    fun readCountry(OnSuccess: (list:MutableList<Country>) -> Unit, onFail: (String) -> Unit)

    fun signOut()

    fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit)

    fun authoruzation(onSuccess: () -> Unit, onFail: (String) -> Unit)

}