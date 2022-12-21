package ru.fi.mycursprojectgotravel.screens.list

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.dfl.newsapi.NewsApiRepository
import com.dfl.newsapi.enums.Category
import com.dfl.newsapi.enums.Country
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.runBlocking
import ru.fi.mycursprojectgotravel.R
import ru.fi.mycursprojectgotravel.getCodeForNews
import ru.fi.mycursprojectgotravel.model.News
import ru.fi.mycursprojectgotravel.translateGoogle
import ru.fi.mycursprojectgotravel.utils.SELECTED_COUNTRY
import ru.fi.mycursprojectgotravel.utils.TEXT_SIZE
import ru.fi.mycursprojectgotravel.utils.VALUE_LOCALE_NEWS
import java.util.*

private const val NEWS_API_KEY = "310f1b56ed3e4f60b68464c2b8b5275a"

@Composable
fun NewsScreen(){

    var resultList : MutableList<News> by remember { mutableStateOf(mutableListOf()) }

    getCodeForNews()

    getNewsCountry{
        resultList = it
    }

    Box(modifier = Modifier.padding( top = 50.dp, bottom = 50.dp)){
        if(resultList.isEmpty()){
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
        }else {
            ListNews(list = resultList)
        }
    }

}

@Composable
fun ListNews(list: MutableList<News>){
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        items(items = list ){ item ->
            NewsItem(item)
        }
    }
}

@Composable
fun NewsItem(itemNews: News){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        shape = RoundedCornerShape(3.dp),
        elevation = 5.dp,
    ){
        val context = LocalContext.current
        val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(itemNews.url)) }
        Box(){
            Column(modifier = Modifier.clickable{ context.startActivity(intent)}){
                Text(text = itemNews.tittle, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Text(text = itemNews.description, fontSize = TEXT_SIZE.value)
                Image(
                    painter = rememberImagePainter(
                        data = itemNews.image,
                        builder ={
                            crossfade(false)
                            placeholder(R.drawable.crocodile)
                        }

                    ),
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(5.dp)
                        .height(250.dp)


                )
            }
        }
    }
}


fun getNewsCountry(onSuccess:(MutableList<News>) -> Unit){
    var listNews : MutableList<News> = mutableListOf()
    var i = 0
    val newsApiRepository = NewsApiRepository(NEWS_API_KEY)
    newsApiRepository.getTopHeadlines(category = Category.GENERAL, country = VALUE_LOCALE_NEWS as Country, pageSize = 5, page = 1)
        .subscribeOn(Schedulers.io())
        .toFlowable()
        .flatMapIterable { articles -> articles.articles }
        .subscribe({ article ->
            runBlocking{
                var news = News()
                listNews.add(i, news)
                news.tittle = translateGoogle(article.title)
                news.description = translateGoogle(article.description)
                news.image = article.urlToImage
                news.url = article.url
                ++i
            }
                if(listNews.size == 5){
                    onSuccess(listNews)
                }
                   },

            { t ->
                Log.d("getTopHeadlines error", t.message.toString())

            })
}

@SuppressLint("CheckResult")
fun getNews(country: String): Int{
    val newsApiRepository = NewsApiRepository(NEWS_API_KEY)
    var result: Int? = null
    newsApiRepository.getTopHeadlines(category = Category.GENERAL, country = country as Country, pageSize = 5, page = 1)
//        .subscribeOn(Schedulers.io())
//        .toFlowable()
//        .flatMapIterable { articles ->
//            result = articles.totalResults as Int
//        }
    return result!!
}