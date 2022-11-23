package ru.fi.mycursprojectgotravel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.fi.mycursprojectgotravel.utils.REPOSITORY

class AuthorizationViewModel(application: Application): AndroidViewModel(application) {

    fun rejectAuth(): Boolean{
        return false
    }


    fun authorization(onSuccess:() -> Unit){
        REPOSITORY.authoruzation(
            {onSuccess()},
            {
               Log.d("checkData", "Error ${it}")
            }
        )
    }

}

class AuthorizationViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AuthorizationViewModelFactory::class.java)){
            return AuthorizationViewModelFactory(application = application) as T
        }
        throw IllegalStateException("")
    }

}
