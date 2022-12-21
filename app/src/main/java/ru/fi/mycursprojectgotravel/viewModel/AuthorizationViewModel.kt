package ru.fi.mycursprojectgotravel.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.fi.mycursprojectgotravel.model.Country
import ru.fi.mycursprojectgotravel.utils.REPOSITORY
import java.util.*


class AuthorizationViewModel(application: Application): AndroidViewModel(application) {

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
