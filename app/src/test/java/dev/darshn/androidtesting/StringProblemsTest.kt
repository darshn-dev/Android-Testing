package dev.darshn.androidtesting


import com.google.common.truth.Truth.assertThat
import dev.darshn.androidtesting.testingClass.StringProblems
import org.junit.Test

class StringProblemsTest{

    @Test
    fun `string with invalid number of brances returns false`(){
        val  result = StringProblems.checkBrances("(jhsdf))")
        assertThat(result).isFalse()
    }


    @Test
    fun `string with equal number of braces return true`(){
        val result = StringProblems.checkBrances("(ooo)")
        assertThat(result).isTrue()
    }
}