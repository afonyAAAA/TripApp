package ru.fi.mycursprojectgotravel.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.fi.mycursprojectgotravel.model.Food
import ru.fi.mycursprojectgotravel.utils.REPOSITORY
import java.util.*

class FoodViewModel(application: Application): AndroidViewModel(application) {

    fun getCollectionFood(onSuccess:(MutableList<Food>) -> Unit){
        REPOSITORY.readFood(
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
    fun filterFood(element : Boolean,vegetable : Boolean): Boolean{
        return vegetable == element
    }


}

class FoodViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FoodViewModel::class.java)){
            return FoodViewModel(application = application) as T
        }
        throw IllegalStateException("")
    }

}