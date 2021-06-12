package dev.darshn.androidtesting

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import dev.darshn.androidtesting.testingClass.ResourceComparer
import org.junit.After
import org.junit.Before

import org.junit.Test

class ResourceComparerTest{

    private lateinit var resourceComparer : ResourceComparer

    @Before
    fun setup(){
        resourceComparer = ResourceComparer()
    }

    @After
    fun teardown(){

    }

    @Test
    fun stringResourceSameAsGivenString_returnsTrue(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context,R.string.app_name,"Android Testing")
        assertThat(result).isTrue()
    }


    @Test
    fun stringResourceNotSameAsGivenString_returnsFalse(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context,R.string.app_name,"Androidhkdfj")
        assertThat(result).isFalse()
    }
}