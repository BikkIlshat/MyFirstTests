package com.geekbrains.myfirsttests

class PasswordComparator {

  companion object {

    var password: String? = ""
    var passwordToCompare: String? = ""

    var array = arrayOf(1)
    var arrayToCompare = arrayOf(1)

    fun comparePasswords(pass1: String, pass2: String): Boolean {
      return pass1 == pass2
    }

    fun setPasswords (pass1: String?, pass2: String?) {
      password = pass1
      passwordToCompare = pass2
    }

    fun setArrays (array1: Array<Int>, array2: Array<Int>) {
      array = array1
      arrayToCompare = array2
    }
  }
}