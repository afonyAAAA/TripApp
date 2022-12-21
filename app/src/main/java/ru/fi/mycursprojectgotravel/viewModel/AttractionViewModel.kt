package ru.fi.mycursprojectgotravel.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.fi.mycursprojectgotravel.model.Attraction
import ru.fi.mycursprojectgotravel.model.City
import ru.fi.mycursprojectgotravel.model.Country
import ru.fi.mycursprojectgotravel.utils.REPOSITORY
import java.util.*


class AttractionViewModel(application: Application): AndroidViewModel(application) {


    fun getCollectionAttraction(onSuccess:(MutableList<Attraction>) -> Unit){
        REPOSITORY.readAttraction(
            {
                onSuccess(it)
            },
            { Log.d("Error", it)}
        )
    }


    fun search(element: String, searchedText : String): Boolean{
        return element.lowercase(Locale.getDefault())
            .contains(searchedText.lowercase(Locale.getDefault()))
    }
}

class AttractionViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AttractionViewModel::class.java)){
            return AttractionViewModel(application = application) as T
        }
        throw IllegalStateException("")
    }

}
