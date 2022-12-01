package ru.fi.mycursprojectgotravel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.fi.mycursprojectgotravel.database.firebase.AppFireBaseRepository
import ru.fi.mycursprojectgotravel.utils.LOGIN
import ru.fi.mycursprojectgotravel.utils.PASSWORD
import ru.fi.mycursprojectgotravel.utils.REPOSITORY


class RegistrationViewModel(application: Application): AndroidViewModel(application){

    fun cancelRegistration(){

    }

    fun rejectRegistration(): Boolean{
        return false
    }

    fun acceptRegistration(){

    }

    fun registration(onSuccess: () -> Unit, onFail : (String) -> Unit){
        Log.d("checkData", "registrationViewModel initdatabase")
        REPOSITORY.connectToDatabase(
            { onSuccess()},
            {
              Log.d("checkData", "Error ${it}")
                onFail(it)
            }

        )
    }



}

class RegistrationViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RegistrationViewModel::class.java)){
            return RegistrationViewModel(application = application) as T
        }
        throw IllegalStateException("")
    }

}