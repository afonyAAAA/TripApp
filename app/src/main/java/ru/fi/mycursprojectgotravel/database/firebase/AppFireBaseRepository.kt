package ru.fi.mycursprojectgotravel.database.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import ru.fi.mycursprojectgotravel.RegistrationViewModel
import ru.fi.mycursprojectgotravel.model.Country
import ru.fi.mycursprojectgotravel.utils.LOGIN
import ru.fi.mycursprojectgotravel.utils.PASSWORD

class AppFireBaseRepository(): DatabaseRepository {

    private val mAuth = FirebaseAuth.getInstance()

    override val readAll: LiveData<List<Country>>
       get() = TODO("Not yet implemented")

    override suspend fun create(country: Country, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun update(country: Country, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(country: Country, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        mAuth.signOut()
    }

    override fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit) {
        mAuth.createUserWithEmailAndPassword(LOGIN, PASSWORD)
            .addOnSuccessListener {
                Log.d("checkData", "regist ok")
                onSuccess()
            }
            .addOnFailureListener{
                Log.d("checkData", "regist failed")
                onFail(it.message.toString())
            }
    }
    override fun authoruzation(onSuccess: () -> Unit, onFail: (String) -> Unit) {
        mAuth.signInWithEmailAndPassword(LOGIN, PASSWORD)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener{
                onFail(it.message.toString())
            }
    }
}
