package ru.fi.mycursprojectgotravel.database.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.fi.mycursprojectgotravel.RegistrationViewModel
import ru.fi.mycursprojectgotravel.model.Country
import ru.fi.mycursprojectgotravel.utils.LOGIN
import ru.fi.mycursprojectgotravel.utils.PASSWORD
import kotlin.concurrent.thread

class AppFireBaseRepository(): DatabaseRepository {

    private val mAuth = FirebaseAuth.getInstance()

    private val db = Firebase.firestore

    override suspend fun create(country: Country, onSuccess: () -> Unit) {

    }

    override suspend fun update(country: Country, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(country: Country, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun readCountry(onSuccess: (list:MutableList<Country>) -> Unit, onFail: (String) -> Unit){
        var countryList:MutableList<Country> = arrayListOf()
        db.collection("Countries")
            .get()
            .addOnSuccessListener{ result ->
                var i = 0
                for(element in result){
                    if(i < result.size()){
                        var country = Country()
                        countryList.add(i,country)
                        country.id = element.id
                        country.nameCountry = element.get("NameCountry").toString()
                        country.generalDescription = element.get("GeneralDescription").toString()
                        ++i
                    }
                }
                onSuccess(countryList)
            }
            .addOnFailureListener { exception ->
               onFail(exception.toString())
            }
    }

    override fun signOut() {
        mAuth.signOut()
    }

    override fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit) {
        mAuth.createUserWithEmailAndPassword(LOGIN, PASSWORD)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener{
                onFail(it.message.toString())
            }
    }
    override fun authoruzation(onSuccess: () -> Unit, onFail: (String) -> Unit) {
        mAuth.signInWithEmailAndPassword(LOGIN, PASSWORD)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener{
                onFail(it.message.toString())
            }
    }
}
