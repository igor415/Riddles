package com.varivoda.igor.zagonetke.ui.shared

import android.content.SharedPreferences

class Preferences(private val sharedPreferences: SharedPreferences) {

    fun getUser(): String = sharedPreferences.getString("username key", null) ?: "null"

    fun setUser(user: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username key", user)
        editor.apply()
    }
    fun clear(value: String) {
        sharedPreferences.edit().remove(value).apply()
    }
    fun saveToken(token: String){
        val editor = sharedPreferences.edit()
        editor.putString("token key", "Bearer $token")
        editor.apply()
    }
    fun getToken():String{
        return sharedPreferences.getString("token key",null) ?: "null"
    }

    fun setJoker(){
        val editor = sharedPreferences.edit()
        editor.putBoolean("joker", false)
        editor.apply()
    }

    fun jokerAvailable(): Boolean{
        return sharedPreferences.getBoolean("joker",true)
    }

    fun geCurrentUserEmail(): String = sharedPreferences.getString("username email", null) ?: "null"

    fun setCurrentUserEmail(email: String){
        val editor = sharedPreferences.edit()
        editor.putString("username email", email)
        editor.apply()
    }

}