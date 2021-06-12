package dev.darshn.androidtesting.testingClass

object StringProblems {

    fun checkBrances(string: String):Boolean{
        return string.count { it == '(' } == string.count { it == ')' }
    }
}