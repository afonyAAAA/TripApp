package ru.fi.mycursprojectgotravel.utils

import ru.fi.mycursprojectgotravel.database.firebase.AppFireBaseRepository
import ru.fi.mycursprojectgotravel.database.firebase.DatabaseRepository
import ru.fi.mycursprojectgotravel.model.Country

lateinit var LOGIN: String
lateinit var PASSWORD: String

val REPOSITORY : DatabaseRepository = AppFireBaseRepository()

