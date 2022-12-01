package ru.fi.mycursprojectgotravel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.fi.mycursprojectgotravel.database.firebase.AppFireBaseRepository
import ru.fi.mycursprojectgotravel.navigation.NavRoutes
import ru.fi.mycursprojectgotravel.utils.REPOSITORY


class AuthorizationViewModel(application: Application): AndroidViewModel(application) {

    fun rejectAuth(): Boolean{
        return false
    }


    fun authorization(onSuccess:() -> Unit, onFail: (String) -> Unit){
        REPOSITORY.authoruzation(
            { onSuccess() },
            {
                onFail(it)
            }
        )
    }

}

class AuthorizationViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AuthorizationViewModel::class.java)){
            return AuthorizationViewModel(application = application) as T
        }
        throw IllegalStateException("")
    }

}
