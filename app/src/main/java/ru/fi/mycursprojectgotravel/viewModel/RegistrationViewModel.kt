package ru.fi.mycursprojectgotravel.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.fi.mycursprojectgotravel.utils.REPOSITORY


class RegistrationViewModel(application: Application): AndroidViewModel(application){


    fun registration(onSuccess: () -> Unit, onFail : (String) -> Unit){
        Log.d("checkData", "registrationViewModel initdatabase")
        REPOSITORY.registration(
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