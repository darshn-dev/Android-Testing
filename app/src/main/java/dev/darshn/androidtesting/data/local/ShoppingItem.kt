package dev.darshn.androidtesting.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ShoppingItem")
data class ShoppingItem(
    var name:String,
    var amount:Int,
    var price :Float,
    var imgUrl:String,
    @PrimaryKey(autoGenerate = true)
    var id:Int? =null
)