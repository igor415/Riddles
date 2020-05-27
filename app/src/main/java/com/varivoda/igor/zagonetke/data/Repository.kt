package com.varivoda.igor.zagonetke.data

import com.varivoda.igor.zagonetke.data.remote.ApiService
import com.varivoda.igor.zagonetke.models.*
import com.varivoda.igor.zagonetke.ui.shared.bodyOrNull

class Repository(private val api: ApiService) {

    fun validateUsernameAndPassword(loginEntry: LoginEntry): LoginResponse?{
        val result = api.validateUsernameAndPassword(loginEntry).execute()
        return result.bodyOrNull()
    }
    fun getAllRiddles(token: String):List<Riddle>?{
        val response = api.getAllRiddles(token).execute()
        return response.bodyOrNull()
    }
    fun changePassword(forgotPasswordEntry: ForgotPasswordEntry): Boolean{
        val result = api.changePassword(forgotPasswordEntry).execute()
        return result.isSuccessful
    }
    fun createUser(user: User):Boolean{
        val result = api.createUser(user).execute()
        return result.isSuccessful
    }
    fun getHighScores(token: String): List<ScoresEntry>?{
        val response = api.getHighScores(token).execute()
        return response.bodyOrNull()
    }
    fun getScoreForUser(token: String,username: String): ScoresEntry?{
        val response = api.getScoreForUser(token, username).execute()
        return response.bodyOrNull()
    }
    fun updateScoreForUser(token: String, scoresEntry: ScoresEntry): Boolean{
        val result = api.updateScoreForUser(token,scoresEntry).execute()
        return result.isSuccessful
    }

    fun getIntegerScoreForUser(token: String,username: String): IntegerScore?{
        val result = api.getIntegerScoreForUser(token,username).execute()
        return result.bodyOrNull()
    }

    fun resetStatsForUser(token: String,scoresEntry: ScoresEntry){
        api.resetStatsForUser(token, scoresEntry).execute()
    }

    fun addUserToMailingList(token: String,mailingList: Mailing_list){
        api.addUserToMailingList(token,mailingList).execute()
    }
    fun deleteUserFromMailingList(token: String,username: String){
        api.deleteUserFromMailingList(token,username).execute()
    }

    fun getEmailForUsername(token: String,username: String): String? {
        val result = api.getEmailForUsername(token,username).execute()
        return result.bodyOrNull()
    }
}



