package ru.fi.mycursprojectgotravel.database.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import ru.fi.mycursprojectgotravel.model.*
import ru.fi.mycursprojectgotravel.translateGoogle
import ru.fi.mycursprojectgotravel.utils.*

class AppFireBaseRepository(): DatabaseRepository {

    private val mAuth = FirebaseAuth.getInstance()

    private val db = Firebase.firestore

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
                        if(LOCALIZATION_USER == "ru"){
                            country.id = element.id
                            country.code = element.get("code").toString()
                            country.nameCountry = element.get("NameCountry").toString()
                            country.generalDescription = element.get("GeneralDescription").toString()
                            country.generalDescriptionCulture = element.get("GeneralDescriptionCulture").toString()
                            country.habitsAndCustomsDescription = element.get("HabitsAndCustomsDescription").toString()
                            country.architectureDescription = element.get("ArchitectureDescription").toString()
                            country.musicDescription = element.get("MusicDescription").toString()
                            country.imageCountry = element.get("ImageCountry").toString()
                            country.imageCountryDescription = element.get("imageCountryDescription").toString()
                            country.imageCountryCulture = element.get("imageCountryCulture").toString()
                        }else{
                            runBlocking {
                                country.id = element.id
                                country.code = element.get("code").toString()
                                country.nameCountry = translateGoogle(element.get("NameCountry").toString())
                                country.generalDescription = translateGoogle(element.get("GeneralDescription").toString())
                                country.generalDescriptionCulture = translateGoogle(element.get("GeneralDescriptionCulture").toString())
                                country.habitsAndCustomsDescription = translateGoogle(element.get("HabitsAndCustomsDescription").toString())
                                country.architectureDescription = translateGoogle(element.get("ArchitectureDescription").toString())
                                country.musicDescription = translateGoogle(element.get("MusicDescription").toString())
                                country.imageCountry = element.get("ImageCountry").toString()
                                country.imageCountryDescription = element.get("imageCountryDescription").toString()
                                country.imageCountryCulture = element.get("imageCountryCulture").toString()
                            }
                        }
                        ++i
                    }
                }
                onSuccess(countryList)
            }
            .addOnFailureListener { exception ->
               onFail(exception.toString())
            }
    }

    override fun readFood(onSuccess: (list: MutableList<Food>) -> Unit, onFail: (String) -> Unit) {
        var foodList:MutableList<Food> = arrayListOf()
        db.collection("Foods")
            .whereEqualTo("idCountry", SELECTED_COUNTRY.id)
            .get()
            .addOnSuccessListener { result ->
                var i = 0
                for(element in result){
                    if(i < result.size()){
                        var food = Food()
                        foodList.add(i, food)
                        if(LOCALIZATION_USER == "ru"){
                            food.id = element.id
                            food.image = element.get("image").toString()
                            food.name = element.get("name").toString()
                            food.description = element.get("description").toString()
                            food.idCountry = element.get("idCountry").toString()
                            food.vegetable = element.get("vegetable") as Boolean
                        }else{
                            runBlocking {
                                food.id = element.id
                                food.image = element.get("image").toString()
                                food.name = translateGoogle(element.get("name").toString())
                                food.description = translateGoogle(element.get("description").toString())
                                food.idCountry = element.get("idCountry").toString()
                                food.vegetable = element.get("vegetable") as Boolean
                            }
                        }
                        ++i
                    }
                }
                onSuccess(foodList)
            }
            .addOnFailureListener{
                onFail(it.message.toString())
            }
    }

    override fun readAttraction(onSuccess: (list: MutableList<Attraction>) -> Unit, onFail: (String) -> Unit) {
        var attractionsList:MutableList<Attraction> = arrayListOf()
        db.collection("Attractions")
            .whereEqualTo("idCountry", SELECTED_COUNTRY.id)
            .get()
            .addOnSuccessListener { result ->
                var i = 0
                for(element in result){
                    if(i < result.size()){
                        var attraction = Attraction()
                        attractionsList.add(i, attraction)
                        if(LOCALIZATION_USER == "ru"){
                            attraction.id = element.id
                            attraction.image = element.get("image").toString()
                            attraction.name = element.get("name").toString()
                            attraction.description = element.get("description").toString()
                            attraction.idCountry = element.get("idCountry").toString()
                        }else{
                            runBlocking {
                                attraction.id = element.id
                                attraction.image = element.get("image").toString()
                                attraction.name = translateGoogle(element.get("name").toString())
                                attraction.description = translateGoogle(element.get("description").toString())
                                attraction.idCountry = element.get("idCountry").toString()
                            }
                        }
                        ++i
                    }
                }
                onSuccess(attractionsList)
            }
            .addOnFailureListener{
                onFail(it.message.toString())
            }
    }

    override fun readCity(onSuccess: (list: MutableList<City>) -> Unit, onFail: (String) -> Unit) {
        var cityList: MutableList<City> = arrayListOf()
        db.collection("Cites")
            .whereEqualTo("idCountry", SELECTED_COUNTRY.id)
            .get()
            .addOnSuccessListener { result ->
                for((i, element) in result.withIndex()){
                    var city = City()
                    cityList.add(i, city)
                    if(LOCALIZATION_USER == "ru"){
                        city.id = element.id
                        city.name = element.get("name").toString()
                        city.description = element.get("description").toString()
                        city.idCountry = element.get("idCountry").toString()
                        city.image = element.get("image").toString()
                    }else{
                        runBlocking {
                            city.id = element.id
                            city.name = translateGoogle(element.get("name").toString())
                            city.description = translateGoogle(element.get("description").toString())
                            city.idCountry = element.get("idCountry").toString()
                            city.image = element.get("image").toString()
                        }
                    }

                }
                onSuccess(cityList)
            }
            .addOnFailureListener{
                onFail(it.message.toString())
            }
    }




//override fun addData(){
//    db.collection("Countries")
//        .add(mapOf(
//            "NameCountry" to "Россия",
//            "GeneralDescription" to "Россия (Российская Федерация) - является самым большим государством мира (около 17 000 000 км², что составляет около 11% площади всей суши Земли, или 13% заселенной человеком суши, что почти вдвое больше, чем у занимающей второе место Канады) и стоит на девятом месте по численности населения.",
//            "GeneralDescriptionCulture" to "Россия - страна с многовековой историей и богатой культурой. Многие архитектурные и исторические памятники на её территории внесены в список Объектов всемирного наследия ЮНЕСКО; в их числе: Московский Кремль и Красная площадь, исторический центр Санкт-Петербурга и дворцово–парковые комплексы его окрестностей, погост Кижи, исторические памятники Новгорода, историко–культурный комплекс Соловецких островов, белокаменные памятники Владимиро–Суздальской Земли и церковь Бориса и Глеба в Кидекше, Троице-Сергиева Лавра в Сергиевом Посаде, Церковь Вознесения в Коломенском. Леса занимают около 60% территории страны, среди которых 35 национальных парков и 84 заповедника. В России 2,5 млн. рек и 3 млн. озер.",
//            "HabitsAndCustomsDescription" to "Россияне известны своим гостеприимством. Они любят встречать гостей и с удовольствием ходят к друзьям и знакомым сами. Щедро накрытые столы – главный атрибут таких встреч. Даже если вас пригласили на чашку чая, то лучше не есть перед визитом – русские имеют обыкновение выкладывать перед гостями едва ли не все имеющиеся дома запасы, в том числе и собственного приготовления. Нет ничего обиднее для хозяйки, чем зрелище пустого стола в разгар посиделок. В ее представлении, это признак того, что гости еще голодны, и еды было припасено недостаточно.\n" +
//                    "\n" +
//                    "Россияне обычно плюют через левое плечо, если дорогу перебежала черная кошка; стучат по дереву, чтобы не сглазить задуманное, и не свистят в доме, чтобы не остаться без денег. Если возвращаются в помещение, забыв какую-то мелочь, то перед уходом обязательно смотрятся в зеркало. Чтобы поездка выдалась удачной, считают русские, нужно обязательно молча посидеть перед дорогой. Ничего хорошего, согласно поверьям, не сулит разбитое зеркало или просыпанная соль. Перед важными событиями (например, перед сдачей экзамена) россияне желают друг другу «ни пуха, ни пера». В ответ нужно сказать: «К черту!», иначе не сработает.",
//            "ArchitectureDescription" to "Ру́сская архитекту́ра развивалась в русле традиций, основы которых сформировались в культуре Византии, а затем в Древнерусском государстве. После падения Киева под натиском монгольской империи история древнерусской архитектуры продолжалась во Владимиро-Суздальском княжестве, Новгородской и Псковской республиках, Русском царстве. Новая история архитектуры складывалась на основе национальных традиций под воздействием внешних, западно-европейских и восточных, влияний в Российской империи, Советском Союзе и современной Российской Федерации. ",
//            "MusicDescription" to "Русская народная музыка (русский музыкальный фольклор) — традиционная музыка русского народа, является частью русской народной культуры. В середине XIX века возник новый жанр — частушка. Одним из первых фольклорных коллективов в России стала Славянская капелла Дмитрия Агренева-Славянского, основанная в 1868 году, которая продвигала русскую и славянскую народную песню в России и за рубежом, нередко в эстрадных обработках.",
//            "ImageCountry" to "https://firebasestorage.googleapis.com/v0/b/gotravel-222f6.appspot.com/o/Russia%2F%D0%BB%D0%B8%D1%81%D1%82.jpg?alt=media&token=ccec0be8-bbd1-4efb-9a99-088e8c1ecfad",
//            "imageCountryDescription" to "https://firebasestorage.googleapis.com/v0/b/gotravel-222f6.appspot.com/o/Russia%2F%D0%BE%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5.jpg?alt=media&token=66110c25-ee71-43af-a09c-4c0ce5dda235",
//            "imageCountryCulture" to "https://firebasestorage.googleapis.com/v0/b/gotravel-222f6.appspot.com/o/Russia%2F%D0%BA%D1%83%D0%BB%D1%8C%D1%82%D1%83%D1%80%D0%B0.jpg?alt=media&token=ab8c0043-9268-4a6b-8226-6540f3da81d6"
//        ))
//}

//    override fun addData(){
//        var listName: ArrayList<String> = arrayListOf("Пельмени", "Блины", "Солянка", "Окрошка", "Щи")
//        var listDescription: ArrayList<String> = arrayListOf("Когда вспоминаешь названия русских блюд, пельмени — первое, что приходит на слух. Впервые их начали готовить на Урале более 600 лет назад. Форма пельменей напоминает ушную раковину— от этого сходства и произошло название, означающее «хлебное ухо».",
//            "Это знаменитая русская еда из жареного тонкого теста. Изначально блины считались обрядовой пищей, готовились на Масленицу или на поминки. Круглая форма блинов символизировала солнце, а также жизненный круг, цикл. Со временем ритуальный смысл блинов утратили, стали печь по любому поводу. К столу блины подают с медом, маслом, вареньем.",
//            "Это вкусное блюдо готовят на бульоне из мяса, грибов, рыбы. В бульон обязательно добавляют кисло-соленые продукты: огурцы или грибы, маслины, лимон, рассол. В мясной бульон кладут отварное или жареное мясо, солонину, копчености или колбасные изделия.",
//            "Это холодный суп из нарезанных мелкими кубиками овощей, солений, мяса или рыбы. Раньше ее делали из остатков заготовок. Поэтому мясо было нескольких видов (птица, говядина, свинина) и разного способа приготовления (отварное, жареное, копченое). Сейчас в окрошке используют отварную говядину, курятину или колбасные изделия. Добавляют свежие или соленые огурчики, отварной картофель, морковь. Затем мясо-овощную смесь перемешивают с пряной заправкой. Кладут зелень, закрывают крышкой и помещают в холодное место минимум на 30 минут. Перед подачей на стол блюдо заливают квасом и добавляют ложку сметаны. В классическом варианте для заливки используют окрошечный квас, но часто его заменяют на хлебный.",
//            "Щи — традиционная русская еда. Это суп с характерным кисловатым вкусом. Обычно щи готовят из квашеной капусты, добавляя рассол. Есть зеленые щи, которые готовят из щавеля. Технология приготовления супа проста: подготовленные ингредиенты без предварительной тепловой обработки кладут в кипящий бульон и отваривают. Только квашеную капусту заранее тушат до мягкого состояния. В щи, как и другие блюда русской кухни, добавляют зелень и коренья."
//                )
//        var vegetable: ArrayList<Boolean> = arrayListOf(false, true, false, true, true)
//        var imageList: ArrayList<String> = arrayListOf("https://firebasestorage.googleapis.com/v0/b/gotravel-222f6.appspot.com/o/Russia%2FFood%2F%D0%BF%D0%B5%D0%BB%D1%8C%D0%BC%D0%B5%D0%BD%D0%B8%20%D0%B1.jpg?alt=media&token=51cd1a25-0192-4f27-bff9-6553bd8c5067",
//            "https://firebasestorage.googleapis.com/v0/b/gotravel-222f6.appspot.com/o/Russia%2FFood%2F%D0%B1%D0%BB%D0%B8%D0%BD%D1%8B%20%D0%B1.jpg?alt=media&token=340e842c-2212-45c6-ae3e-1b1a30532cdb",
//            "https://firebasestorage.googleapis.com/v0/b/gotravel-222f6.appspot.com/o/Russia%2FFood%2F%D1%81%D0%BE%D0%BB%D1%8F%D0%BD%D0%BA%D0%B0%20%D0%B1.jpg?alt=media&token=e27557b1-9826-4b8d-a190-28c8b600cd97",
//            "https://firebasestorage.googleapis.com/v0/b/gotravel-222f6.appspot.com/o/Russia%2FFood%2F%D0%BE%D0%BA%D1%80%D0%BE%D1%88%D0%BA%D0%B0%20%D0%B1.jpg?alt=media&token=8a0089fd-41af-44d5-8e86-d3a4d22d0105",
//            "https://firebasestorage.googleapis.com/v0/b/gotravel-222f6.appspot.com/o/Russia%2FFood%2F%D1%89%D0%B8%20%D0%B1.jpg?alt=media&token=8c1bc7e7-3f2f-4ba0-b296-79c9ca4d000c"
//            )
//        for((i) in listName.withIndex()) {
//            db.collection("Foods")
//                .add(
//                    mapOf(
//                        "name" to listName[i],
//                        "description" to listDescription[i],
//                        "idCountry" to "1n3lNABq8jnsJi9yUsg0",
//                        "vegetable" to vegetable[i],
//                        "image" to imageList[i]
//                    )
//                )
//        }
//
//    }

//    override fun addData(){
//        var listName: ArrayList<String> = arrayListOf("Томск", "Москва", "Новосибирск", "Краснодар", "Санкт-Петербург")
//        var listDescription: ArrayList<String> = arrayListOf(
//            "Томск — город в России, административный центр одноимённых области и района, расположенный на востоке Западной Сибири на берегу реки Томи. Старейший в Сибири крупный образовательный, научный и инновационный центр, насчитывающий 9 вузов, 15 НИИ, особую экономическую зону технико-внедренческого типа и 6 бизнес-инкубаторов.\n" +
//                    "\n" +
//                    "Является членом Ассоциации сибирских и дальневосточных городов и ассоциации «Сеть главных городов Азии».",
//            "Москва — столица России, город федерального значения, административный центр Центрального федерального округа и центр Московской области, в состав которой не входит[6]. Крупнейший по численности населения город России и её субъект — 13 010 112 человек (2021), самый населённый из городов, полностью расположенных в Европе, занимает 22-е место среди городов мира по численности населения, крупнейший русскоязычный город в мире. Центр Московской городской агломерации. Самый крупный город Европы по площади.",
//            "Новосибирск — третий по численности населения город России (после Москвы и Санкт-Петербурга). Самый большой город в Азиатской части России. За ним следуют Екатеринбург и Челябинск.\n" +
//                    "\n" +
//                    "Административный центр Сибирского федерального округа, Новосибирской области и Новосибирского района (в состав последнего не входит), центр Западно-Сибирского экономического района. Город областного значения, образует муниципальное образование город Новосибирск со статусом городского округа, являющегося самым населённым муниципальным образованием в стране. Также в Новосибирске находятся Представительство президента РФ в Сибирском федеральном округе, Президиум Сибирского отделения Российской академии наук, Пятый Апелляционный суд общей юрисдикции и Кассационный военный суд Российской Федерации.\n" +
//                    "\n" +
//                    "Город является центром Новосибирской агломерации. Крупнейший торговый, деловой, культурный, транспортный, образовательный и научный центр Сибири. ",
//            "Краснодар — город на юго-западе России, расположенный на правом берегу реки Кубани, на расстоянии 120 км от Чёрного моря (по автодороге от пос. Джубга), 140 км — от Азовского моря (по автодороге от станицы Голубицкой) и 1300 км — к югу от Москвы (по автодороге М-4 «Дон»).Перейти к разделу «География» Административный центр Краснодарского края. Вместе с прилегающими сельскими населёнными пунктами образует городской округ город Краснодар." +
//                    "\n" +
//                    "Крупный экономический и культурный центр Северного Кавказа и Южного федерального округа, центр историко-географической области Кубань. Неофициально именуется столицей Кубани, а также столицей Юга России. ",
//            "Санкт-Петербург — второй по численности населения город России. Город федерального значения. Административный центр Северо-Западного федерального округа. Основан 16 (27) мая 1703 года царём Петром I. В 1714—1728 и 1732—1918 годах — столица Российского государства.\n" +
//                    "\n" +
//                    "Назван в честь святого Петра — небесного покровителя царя-основателя, но со временем стал всё больше ассоциироваться с именем самого Петра I. Город исторически и культурно связан с рождением Российской империи и вхождением России в современную историю в роли европейской великой державы.\n" +
//                    "\n" +
//                    "Расположен на северо-западе страны на побережье Финского залива и в устье реки Нева. Граничит с Ленинградской областью, также имеет морские границы с Финляндией и Эстонией». "
//        )
//        var imageList: ArrayList<String> = arrayListOf(
//            "https://firebasestorage.googleapis.com/v0/b/gotravel-222f6.appspot.com/o/Russia%2FCity%2F%D1%82%D0%BE%D0%BC%D1%81%D0%BA%20%D0%B3.jpg?alt=media&token=2a6dbf19-e45a-494d-80e1-d5af66bebc95",
//            "https://firebasestorage.googleapis.com/v0/b/gotravel-222f6.appspot.com/o/Russia%2FCity%2F%D0%BC%D0%BE%D1%81%D0%BA%D0%B2%D0%B0%20%D0%B3.jpg?alt=media&token=aee63864-e940-4a06-a3cf-ce672af2af06",
//            "https://firebasestorage.googleapis.com/v0/b/gotravel-222f6.appspot.com/o/Russia%2FCity%2F%D0%BD%D0%BE%D0%B2%D0%BE%D1%81%D0%B8%D0%B1%20%D0%B3.jpg?alt=media&token=3748c575-8f9a-4539-913c-718814e011e1",
//            "https://firebasestorage.googleapis.com/v0/b/gotravel-222f6.appspot.com/o/Russia%2FCity%2F%D0%BA%D1%80%D0%B0%D1%81%D0%BD%D0%BE%D0%B4%D0%B0%D1%80%20%D0%B3.jpeg?alt=media&token=ff8f52f5-2bfe-46c3-ac0f-19d3328d1e8b",
//            "https://firebasestorage.googleapis.com/v0/b/gotravel-222f6.appspot.com/o/Russia%2FCity%2F%D1%81%D0%B0%D0%BD%D0%BA%D1%82%20%D0%B3.jpg?alt=media&token=1f4bdb4f-9258-4ae0-bc04-c9f0cbe0ff53"
//        )
//        for((i) in listName.withIndex()) {
//            db.collection("Cites")
//                .add(
//                    mapOf(
//                        "name" to listName[i],
//                        "description" to listDescription[i],
//                        "idCountry" to "T22toUsW962H1U5pIpkR",
//                        "image" to imageList[i]
//                    )
//                )
//        }
//
//    }

    override fun addData(){
        var listName: ArrayList<String> = arrayListOf("Памятник Чехову", "Кремль", "Ленские столбы", "Ласточкино гнездо", "Куршская коса")
        var listDescription: ArrayList<String> = arrayListOf(
            "Двухметровый бронзовый памятник А. П. Чехову установили в 2004 году на набережной в честь 400-летия города Томска. Автором памятника является известный скульптор Леонтий Усов. Монумент был возведен за счет народных средств. Писатель изображен в карикатурном и в гротескном виде: в нелепой шляпе, в пальто, перекошенных набок очках, а также босиком и с довольно непропорциональными большими ногами. За спиной у него размещен зонт, а внизу на постаменте виднеется надпись: “Антон Павлович в Томске глазами пьяного мужика, лежащего в канаве и не читавшего «Каштанку»”! Таким образом, город как бы «отомстил» Чехову, который, пробыв здесь неделю, проездом на Сахалин в 1890 году, отозвался о нем довольно нелестным образом.",
            "Кремль — укреплённое ядро исторического русского города, центральная и наиболее древняя его часть. Слово «кремль» с XIV века получило распространение в Северо-Восточной Руси, постепенно заменив первоначальное название детинец. В структуре древнерусских городов к обнесённому крепостной стеной кремлю-детинцу в большинстве случаев примыкали один или два более крупных по площади окольных города, которые также были укреплены. Неукреплённые части города назывались посадами.\n" +
                    "\n" +
                    "В кремле, как правило, находился княжеский дворец, главные каменные храмы, усадьбы феодальной знати и ремесленные мастерские, обслуживавшие княжеский двор. Для повышения обороноспособности кремли пытались строить с учётом естественных преимуществ рельефа местности — на мысах у слияния двух рек, на возвышенностях и т. д.",
            "Ленские столбы – вертикальные скальные образования, протянувшиеся на 40 км вдоль берега реки Лена в Якутии. Их происхождение относят к раннему кембрийскому периоду (530 миллионов лет назад). Издалека столбы кажутся монолитной стеной, спускающейся в реку. Суровые и в то же время величественные, они завораживают своей необыкновенной красотой.",
            "Миниатюрный неоготический замок находится на краю сорокаметровой отвесной Аврориной скалы на юге Крыма. Строение, получившие название «Ласточкино гнездо», благодаря своим размерам (ширина– 10 метров, длина – 20 метров, высота – 12 метров) и местоположению, было создано в 1912 году по проекту архитектора Леонида Шервуда.",
            "Песчаный полуостров на юго-востоке Балтийского моря. Одно из наиболее больших эоловых (созданных под воздействием ветра) образований в мире. Укреплять песчаные образования, разрушаемые морем и ветром, люди начали еще в средние века. К концу XVII-XVIII вв. площадь лесов на косе сократилась до 10%. Пески стали засыпать поселения, дороги и уцелевшие деревья. В середине XIX века были начаты интенсивные работы по восстановлению лесов. Сегодня лесополоса занимает 2/3 территории Куршской косы."
        )
        var imageList: ArrayList<String> = arrayListOf(
            "https://firebasestorage.googleapis.com/v0/b/gotravel-222f6.appspot.com/o/Russia%2FAttraction%2F%D1%87%D0%B5%D1%85%D0%BE%D0%B2%20%D0%B4.jpg?alt=media&token=3c381827-5111-44a0-9a81-69be4bfb5fb9",
            "https://firebasestorage.googleapis.com/v0/b/gotravel-222f6.appspot.com/o/Russia%2FAttraction%2F%D0%BA%D1%80%D0%B5%D0%BC%D0%BB%D1%8C%20%D0%B4.jpg?alt=media&token=b504642f-b91e-4be7-991c-89dbf47b8cfc",
            "https://firebasestorage.googleapis.com/v0/b/gotravel-222f6.appspot.com/o/Russia%2FAttraction%2F%D1%81%D0%BA%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D0%B5%20%D0%B4.jpg?alt=media&token=b0b67233-8a84-44c1-8c3d-32fde4bcc9cb",
            "https://firebasestorage.googleapis.com/v0/b/gotravel-222f6.appspot.com/o/Russia%2FAttraction%2F%D0%B3%D0%BD%D0%B5%D0%B7%D0%B4%D0%BE%20%D0%B4.jpg?alt=media&token=9de8e166-7af4-490e-a034-720cf685e9dc",
            "https://firebasestorage.googleapis.com/v0/b/gotravel-222f6.appspot.com/o/Russia%2FAttraction%2F%D0%BA%D0%BE%D1%81%D0%B0%20%D0%B4.jpg?alt=media&token=a36fe65b-f504-498b-84c9-ee1b0a29df34"
        )
        for((i) in listName.withIndex()) {
            db.collection("Attractions")
                .add(
                    mapOf(
                        "name" to listName[i],
                        "description" to listDescription[i],
                        "idCountry" to "T22toUsW962H1U5pIpkR",
                        "image" to imageList[i]
                    )
                )
        }

    }


    override fun checkIdFavorite(onSuccess: (Boolean) -> Unit) {
        db.collection("FavoriteUser")
            .whereEqualTo("idUser", ACTIVE_USER.value)
            .get()
            .addOnSuccessListener {
                onSuccess(it.size()!= 0)
            }
    }

    override fun addIdFavorite(onSuccess: (String) -> Unit){
          db.collection("FavoriteUser")
          .add(mapOf(
              "idUser" to ACTIVE_USER.value
          ))
          .addOnSuccessListener {
              onSuccess(it.id)
          }


    }

    override fun addFavorite(
        idElementFavorite: String,
        idFavorite : String,
        onFail: () -> Unit,
        onSuccess: () -> Unit)
    {
            db.collection("CountryFavorites").whereEqualTo("idCountry", idElementFavorite).whereEqualTo("idFavorite", idFavorite).get()
                .addOnSuccessListener{
                    if(it.size() == 0) {
                        db.collection("CountryFavorites")
                            .add(mapOf(
                                "idFavorite" to idFavorite,
                                "idCountry" to idElementFavorite
                            ))
                        onSuccess()
                    }
            }.addOnFailureListener {onFail()}
    }

    override fun addUser(idUser : String) {
        db.collection("Users")
            .document(idUser)
            .set(
                mapOf(
                    "login" to LOGIN
                )
            )
    }

    override fun getIdFavoriteUser(onSuccess: (String) -> Unit, onFail: (String) -> Unit){
        db.collection("FavoriteUser")
            .whereEqualTo("idUser", ACTIVE_USER.value)
            .get()
            .addOnSuccessListener { result ->
                var id = ""
                for(element in result){
                    id = element.id
                }
                onSuccess(id)
            }
            .addOnFailureListener {
                onFail(it.message.toString())
            }
    }

    override fun deleteFavorite(
        idElementFavorite: String,
        idFavorite: String,
    ) {
        db.collection("CountryFavorites")
            .whereEqualTo("idCountry", idElementFavorite)
            .get()
            .addOnSuccessListener {
                for(element in it){
                    db.collection("CountryFavorites").document(element.id).delete()
                }
            }
    }

    override fun readFavorite(
        onSuccess: (MutableList<Country>) -> Unit,
        onFail: (String) -> Unit,
        idFavorite : String,
    ) {

        var countryFavoritesList: MutableList<Country> = arrayListOf()
        getFavoritesUser({
            var i = 0
            for(element in COUNTRY_LIST){
                for(elementFavorite in it){
                    if(element.id == elementFavorite.countryId){
                                    countryFavoritesList.add(i, element)
                    }
                }
            }
            if(countryFavoritesList.isNotEmpty()) onSuccess(countryFavoritesList) else onFail("List is null")
                         },
                idFavorite)
    }


    override fun registration(onSuccess: () -> Unit, onFail: (String) -> Unit) {
        mAuth.createUserWithEmailAndPassword(LOGIN, PASSWORD)
            .addOnSuccessListener {
                val user = FirebaseAuth.getInstance().currentUser
                val nameProfile = UserProfileChangeRequest.Builder().setDisplayName(LOGIN).build()
                user?.updateProfile(nameProfile)
                if (user != null) {
                    addUser(user.uid)
                }
                onSuccess()
            }
            .addOnFailureListener{
                onFail(it.message.toString())
            }
    }
    override fun authoruzation(onSuccess: () -> Unit, onFail: (String) -> Unit) {
        mAuth.signInWithEmailAndPassword(LOGIN, PASSWORD)
            .addOnSuccessListener {
                ACTIVE_USER.value = mAuth.currentUser?.uid.toString()
                DiSPLAY_NAME.value = LOGIN
                onSuccess()
            }
            .addOnFailureListener{
                onFail(it.message.toString())
            }
    }



    override fun getFavoritesUser(
        onSuccess: (MutableList<CountryFavorites>) -> Unit,
        idfavorite : String,
    ) {

            var countryFavoritesList : MutableList<CountryFavorites> = arrayListOf()
            db.collection("CountryFavorites")
                .whereEqualTo("idFavorite", idfavorite)
                .get()
                .addOnSuccessListener { result ->
                    var i = 0
                    for(element in result){
                        var favoritCountry = CountryFavorites()
                        countryFavoritesList.add(i, favoritCountry)
                        favoritCountry.countryId = element.get("idCountry").toString()
                    }
                    onSuccess(countryFavoritesList)
                }
        }


    }


