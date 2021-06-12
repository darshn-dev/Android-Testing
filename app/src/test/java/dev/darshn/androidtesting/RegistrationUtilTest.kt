package dev.darshn.androidtesting

import com.google.common.truth.Truth.assertThat
import dev.darshn.androidtesting.testingClass.RegistrationUtil
import org.junit.Test

class RegistrationUtilTest{

    @Test
    fun `empty user name returns false`(){
        val result = RegistrationUtil.validateRegister(
            "",
            "123",
            "123"
        )

        assertThat(result).isFalse()
    }

    @Test
    fun `valid user name and proper password returns true`(){
        val result = RegistrationUtil.validateRegister(
            "Rahul",
            "123",
            "123"
        )

        assertThat(result).isTrue()
    }


    @Test
    fun `User name alredy exists return false`(){
        val result = RegistrationUtil.validateRegister(
            "Mohan",
            "123",
            "123"
        )

        assertThat(result).isFalse()
    }


    @Test
    fun `Empty password return false`(){
        val result = RegistrationUtil.validateRegister(
            "Mohan",
            "",
            "123"
        )

        assertThat(result).isFalse()
    }


    @Test
    fun `Password doesnt match returns false`(){
        val result = RegistrationUtil.validateRegister(
            "Mohan",
            "6945",
            "123"
        )

        assertThat(result).isFalse()
    }


    @Test
    fun `Password with 2 digit returns false`(){
        val result = RegistrationUtil.validateRegister(
            "Mohan",
            "12",
            "12"
        )

        assertThat(result).isFalse()
    }


}