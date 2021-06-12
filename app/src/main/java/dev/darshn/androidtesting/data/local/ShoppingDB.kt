package dev.darshn.androidtesting.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities =[ShoppingItem::class],
    version = 1
)
abstract class ShoppingDB :RoomDatabase() {
    abstract fun shoppingDao():ShoppingDao
}