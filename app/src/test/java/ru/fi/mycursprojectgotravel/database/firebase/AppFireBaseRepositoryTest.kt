package ru.fi.mycursprojectgotravel.database.firebase

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.mockito.Mock
import org.mockito.MockedStatic
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.mockStatic
import org.mockito.kotlin.mock
import ru.fi.mycursprojectgotravel.model.City
import ru.fi.mycursprojectgotravel.model.Country
import ru.fi.mycursprojectgotravel.model.CountryFavorites
import ru.fi.mycursprojectgotravel.model.Food
import ru.fi.mycursprojectgotravel.screens.ChangeSizeText
import ru.fi.mycursprojectgotravel.screens.list.getNews
//import ru.fi.mycursprojectgotravel.screens.list.getNews
import ru.fi.mycursprojectgotravel.ui.theme.GoTravelTheme
import ru.fi.mycursprojectgotravel.ui.theme.Theme
import ru.fi.mycursprojectgotravel.utils.COUNTRY_LIST
import ru.fi.mycursprojectgotravel.utils.TEXT_SIZE
import ru.fi.mycursprojectgotravel.viewModel.*

var auth : FirebaseAuth = FirebaseAuth.getInstance()
var db : FirebaseFirestore = Firebase.firestore
var japanId : String? = null
var russiaId : String? = null

lateinit var aViewModel: MainViewModel
lateinit var fViewModel: FoodViewModel
lateinit var cViewModel: CityViewModel
lateinit var atViewModel: AttractionViewModel
lateinit var favViewModel: FavoriteViewModel

class Repository : DatabaseRepositoryTest{
    override fun readCountry(
        OnSuccess: (list: MutableList<Country>) -> Unit,
        onFail: (String) -> Unit,
    ) {
        TODO("Not yet implemented")
    }

    override fun readFood(OnSuccess: (list: MutableList<Food>) -> Unit, onFail: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun readCity(OnSuccess: (list: MutableList<City>) -> Unit, onFail: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun readFavorite(
        onSuccess: (MutableList<Country>) -> Unit,
        onFail: (String) -> Unit,
        idFavorite: String,
    ) {
        TODO("Not yet implemented")
    }

    override fun addData() {
        TODO("Not yet implemented")
    }

    override fun addIdFavorite(onSuccess: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun addFavorite(
        idElementFavorite: String,
        idFavorite: String
    ) : Boolean {
        var result: Boolean? = null
        db.collection("CountryFavorites").whereEqualTo("idCountry", idElementFavorite)
            .whereEqualTo("idFavorite", idFavorite).get()
            .addOnSuccessListener {
                if (it.size() == 0) {
                    db.collection("CountryFavorites")
                        .add(mapOf(
                            "idFavorite" to idFavorite,
                            "idCountry" to idElementFavorite
                        ))
                    result = true
                }
            }.addOnFailureListener {
                result = false
            }
        return result!!
    }

    override fun checkIdFavorite(onSuccess: (Boolean) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun addUser(idUser: String) {
        TODO("Not yet implemented")
    }

    override fun registration(login : String, password : String) : Boolean {

        var result: Boolean? = null

        auth.createUserWithEmailAndPassword(login, password).addOnSuccessListener {
             result = true
        }.addOnFailureListener {
             result = false
        }
        return result!!
    }

    override fun authorization(login : String, password : String) : Boolean {
        TODO("Not yet implemented")
    }

    override fun getFavoritesUser(
        onSuccess: (MutableList<CountryFavorites>) -> Unit,
        idfavorite: String,
    ) {
        TODO("Not yet implemented")
    }

    override fun getIdFavoriteUser(onSuccess: (String) -> Unit, onFail: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun deleteFavorite(idElementFavorite: String, idFavorite: String) : Boolean {
        var result: Boolean? = null
        db.collection("CountryFavorites")
            .whereEqualTo("idCountry", idElementFavorite)
            .get()
            .addOnSuccessListener {
                for(element in it){
                    db.collection("CountryFavorites").document(element.id).delete()
                }
                result = true
            }.addOnFailureListener {
                result = false
            }
        return result!!
    }


}

@Suppress("IMPLICIT_CAST_TO_ANY")
internal class AppFireBaseRepositoryTest {

   private val repository = Repository()

    @Test
    fun registrationTest() {
        assertFalse(repository.registration("", "qwerty123"))
        assertFalse(repository.registration("holabola35@ggg.ru", "qwert"))
        assertTrue(repository.registration("holabola35@ggg.ru", "qwerty123"))
        assertFalse(repository.registration("holabola35@ggg.ru", ""))
        assertFalse(repository.registration("holabola@ru", "qwerty123"))
    }

    @Test
    fun authorizationTest(){
        assertFalse(repository.authorization("", "qwerty123"))
        assertFalse(repository.authorization("holabola35@ggg.ru", "qwert"))
        assertFalse(repository.authorization("holabola35@ggg.", "qwerty123"))
        assertTrue(repository.authorization("holabola35@ggg.ru", "qwerty123"))
        assertFalse(repository.authorization("holabola35@ggg.ru", ""))
        assertFalse(repository.authorization("holabola@ru", "qwerty123"))
    }

    @Composable
    @Test
    fun addAndDeleteFavoriteTest(){
        val context = LocalContext.current
        aViewModel = MainViewModel(context.applicationContext as Application)
        COUNTRY_LIST.forEach { country ->
            japanId = if(country.nameCountry == "Япония"){
                country.id
            }else{ null }
            russiaId = if(country.nameCountry == "Россия"){
                 country.id
            }else{ null }
        }

        if (japanId == null || russiaId == null){
            fail("elementId is null")
        }

        assertTrue(repository.addFavorite(japanId!!, "12"))
        assertFalse(repository.addFavorite(japanId!!, "12"))
        assertTrue(repository.deleteFavorite(japanId!!, "12"))
        assertTrue(repository.addFavorite(russiaId!!, "12"))
    }

    @Composable
    @Test
    fun searchText(){
        val context = LocalContext.current
        fViewModel = FoodViewModel(context.applicationContext as Application)
        cViewModel = CityViewModel(context.applicationContext as Application)
        atViewModel = AttractionViewModel(context.applicationContext as Application)
        favViewModel = FavoriteViewModel(context.applicationContext as Application)


        assertTrue(aViewModel.search("Россия", "Росс"))
        assertTrue(fViewModel.search("Блины", "Блины"))
        assertTrue(aViewModel.search("Япония", "Япония"))
        assertTrue(aViewModel.search("США", "США"))
        assertFalse(aViewModel.search("США", "САШ"))
        assertFalse(aViewModel.search("Канада", "Канс"))
        assertTrue(aViewModel.search("Австралия", "А"))
        assertTrue(cViewModel.search("Томск", "Томск"))
        assertTrue(atViewModel.search("Памятник Чехову", "Памятник"))
        assertTrue(favViewModel.search("Япония", "Япон"))
    }

    @Test
    fun filterTest(){
        assertFalse(fViewModel.filterFood(element = true, vegetable = false))
        assertTrue(fViewModel.filterFood(element = true, vegetable = true))
    }

    @Test
    fun getNewsTest(){
        assertNotNull(getNews(country = "Россия"))
        assertNotNull(getNews(country = "Япония"))
        assertNotNull(getNews(country = "Канада"))
        assertNull(getNews(country = ""))
    }

    @SuppressLint("ComposableNaming")
    @Composable
    @Test
    fun changeThemeAppTest(){
       assertEquals(GoTravelTheme(theme = "DARK" as Theme){}, Theme.DARK)
       assertNotEquals(GoTravelTheme(theme = "Orange" as Theme){}, Theme.DARK)
       assertEquals(GoTravelTheme(theme = "DARK" as Theme){}, Theme.DARK)
    }

    @SuppressLint("ComposableNaming")
    @Composable
    @Test
    fun changeSizeTextTest(){
        assertEquals(ChangeSizeText(progress = 1f), TEXT_SIZE.value)
        assertEquals(ChangeSizeText(progress = 2f), TEXT_SIZE.value)
        assertNotEquals(ChangeSizeText(progress = -0f), TEXT_SIZE.value)
    }





}

