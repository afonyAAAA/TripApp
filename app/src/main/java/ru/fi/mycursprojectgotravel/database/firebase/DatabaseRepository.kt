package ru.fi.mycursprojectgotravel.database.firebase

import androidx.lifecycle.LiveData
import ru.fi.mycursprojectgotravel.model.Country

interface DatabaseRepository {

    val readAll: LiveData<List<Country>>

    suspend fun create(country: Country, onSuccess: () -> Unit)

    suspend fun update(country: Country, onSuccess: () -> Unit)

    suspend fun delete(country: Country, onSuccess: () -> Unit)

    fun signOut()

    fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit)

    fun authoruzation(onSuccess: () -> Unit, onFail: (String) -> Unit)

}