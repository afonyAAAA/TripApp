package ru.fi.mycursprojectgotravel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.fi.mycursprojectgotravel.model.Country
import ru.fi.mycursprojectgotravel.utils.REPOSITORY

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val openCountry: MutableLiveData<List<Country>> by lazy{
        MutableLiveData<List<Country>>()
    }

    fun getCollectionCountry(onSuccess:(MutableList<Country>) -> Unit){
         REPOSITORY.readCountry(
             {onSuccess(it)},
             {Log.d("Error", it)}
         )
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