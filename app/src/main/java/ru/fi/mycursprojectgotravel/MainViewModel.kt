package ru.fi.mycursprojectgotravel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.fi.mycursprojectgotravel.model.Country

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val openCountry: MutableLiveData<List<Country>> by lazy{
        MutableLiveData<List<Country>>()
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