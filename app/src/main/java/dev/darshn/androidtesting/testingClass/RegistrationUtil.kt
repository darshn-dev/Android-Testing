package dev.darshn.androidtesting.testingClass

object RegistrationUtil {


    private val existingUsers = listOf("Mohan","Kiran")

    /**
     * Input is not valid if
     * ......UserName or Password is empty
     * ......UserName is already taken
     * ......Password and Confirm password is not matching
     */
    fun validateRegister(userName:String, password:String, confirmPass:String) :Boolean{
       if(userName.isEmpty() || password.isEmpty()){
           return  false
       }

        if(userName in existingUsers){
            return false
        }
        if (password!=confirmPass){
            return false
        }

        if(password.count { it.isDigit() } < 2){
            return false
        }

        return true

    }


    fun checkBraces(string: String) : Boolean{

        return string.count { it == '(' } == string.count { it == ')' }
    }
}