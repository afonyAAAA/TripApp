package ru.fi.mycursprojectgotravel.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.fi.mycursprojectgotravel.model.Country
import ru.fi.mycursprojectgotravel.model.CountryFavorites
import ru.fi.mycursprojectgotravel.utils.REPOSITORY

class CountryViewModel(application: Application): AndroidViewModel(application) {

    fun getInfoCountry(){

    }

    fun addFavorite(id : String, onSuccess:() -> Unit, onFail:() -> Unit){
        REPOSITORY.checkIdFavorite {
            if(it) {
                REPOSITORY.getIdFavoriteUser({ idFavorite ->
                    REPOSITORY.addFavorite(id, idFavorite, {onFail()}, {onSuccess()})
                },{
                    Log.d("Error", it)
                })
            }else {
                REPOSITORY.addIdFavorite { idFavorite ->
                    REPOSITORY.addFavorite(id, idFavorite,{onFail()}, {onSuccess()})
                }
             }
        }
    }
    fun deleteFavorite(idCountry: String){
        REPOSITORY.getIdFavoriteUser({id ->
            REPOSITORY.deleteFavorite(idCountry, id)
        },{

        })
    }

}


class CountryViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CountryViewModel::class.java)){
            return CountryViewModel(application = application) as T
        }
        throw IllegalStateException("")
    }

}