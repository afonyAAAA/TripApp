package ru.fi.mycursprojectgotravel.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.fi.mycursprojectgotravel.model.Country
import ru.fi.mycursprojectgotravel.utils.REPOSITORY
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    fun getCollectionCountry(onSuccess:(MutableList<Country>) -> Unit){
         REPOSITORY.readCountry(
             {
                 onSuccess(it)
             },
             {Log.d("Error", it)}
         )
    }

    fun getListElementFavorite(onSuccess:(MutableList<Country>) -> Unit, onFail:() -> Unit){
        REPOSITORY.getIdFavoriteUser({
            REPOSITORY.readFavorite({onSuccess(it)},{onFail()},it)
        },{onFail()})
    }

    fun add(){
        REPOSITORY.addData()
    }
    fun search(element: String, searchedText : String): Boolean{
        return element.lowercase(Locale.getDefault())
            .contains(searchedText.lowercase(Locale.getDefault()))
    }
}

class MainViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(application = application) as T
        }
        throw IllegalStateException("")
    }

}