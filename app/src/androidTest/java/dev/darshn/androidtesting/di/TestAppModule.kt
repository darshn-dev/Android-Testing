package dev.darshn.androidtesting.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.darshn.androidtesting.data.local.ShoppingDB
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
class TestAppModule {


    @Provides
    @Named("test_db")
    fun provideInMemoryDB(@ApplicationContext context:Context) = Room.inMemoryDatabaseBuilder(context, ShoppingDB::class.java)
        .allowMainThreadQueries()
        .build()
}