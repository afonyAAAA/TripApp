package ru.fi.mycursprojectgotravel.model

import android.app.ActivityManager
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_table")
data class Food(
     @PrimaryKey(autoGenerate = true)
     val id : Int = 0,
     @ColumnInfo
     val title: String,
     @ColumnInfo
     val description: String
)


