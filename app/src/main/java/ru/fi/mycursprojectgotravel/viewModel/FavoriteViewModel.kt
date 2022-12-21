package ru.fi.mycursprojectgotravel.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.fi.mycursprojectgotravel.model.Country
import ru.fi.mycursprojectgotravel.model.CountryFavorites
import ru.fi.mycursprojectgotravel.utils.REPOSITORY
import java.util.*

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {



    fun getListElementFavorite(onSuccess:(MutableList<Country>) -> Unit){
        REPOSITORY.getIdFavoriteUser({
            REPOSITORY.readFavorite({onSuccess(it)},{},it)
        },{})
    }
    fun search(element: String, searchedText : String): Boolean{
        return element.lowercase(Locale.getDefault())
            .contains(searchedText.lowercase(Locale.getDefault()))
    }



}

class FavoriteViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(application = application) as T
        }
        throw IllegalStateException("")
    }

}