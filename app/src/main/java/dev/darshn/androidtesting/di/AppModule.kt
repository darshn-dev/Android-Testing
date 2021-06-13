package dev.darshn.androidtesting.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.darshn.androidtesting.R
import dev.darshn.androidtesting.data.local.ShoppingDB
import dev.darshn.androidtesting.data.local.ShoppingDao
import dev.darshn.androidtesting.data.remoteResponses.PixabayAPI
import dev.darshn.androidtesting.repository.DefaultShoppingRepository
import dev.darshn.androidtesting.repository.ShoppingRepository
import dev.darshn.androidtesting.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(@ApplicationContext context:Context) = Room.databaseBuilder(context, ShoppingDB::class.java, Constants.DATABASE_NAME).build()

    @Singleton
    @Provides
    fun providesShoppingDao(
        database: ShoppingDB
    ) = database.shoppingDao()


    @Provides
    @Singleton
    fun provideDefaultRepo(
        dao: ShoppingDao,
        api: PixabayAPI
    ): ShoppingRepository{
        return DefaultShoppingRepository(dao,api) as ShoppingRepository
    }

    @Singleton
    @Provides
    fun providesPixabayAPI() : PixabayAPI{
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.PIXAURL)
            .build().create(PixabayAPI::class.java)
    }


    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )

}