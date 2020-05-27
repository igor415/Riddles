package com.varivoda.igor.zagonetke.data.remote

import com.varivoda.igor.zagonetke.models.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("login/authenticate")
    fun validateUsernameAndPassword(@Body loginEntry: LoginEntry): Call<LoginResponse>

    @GET("riddle")
    fun getAllRiddles(@Header("Authorization") token: String):Call<List<Riddle>>

    @POST("forgotpassword")
    fun changePassword(@Body forgotPasswordEntry: ForgotPasswordEntry): Call<ResponseBody>

    @POST("register")
    fun createUser(@Body user: User): Call<ResponseBody>

    @GET("score")
    fun getHighScores(@Header("Authorization") token: String): Call<List<ScoresEntry>>

    @GET("score/{username}")
    fun getScoreForUser(@Header("Authorization") token: String, @Path("username") username: String): Call<ScoresEntry>

    @PUT("score")
    fun updateScoreForUser(@Header("Authorization") token: String,@Body scoresEntry: ScoresEntry): Call<ResponseBody>

    @GET("score")
    fun getIntegerScoreForUser(@Header("Authorization") token: String,@Query("user")username: String):Call<IntegerScore>

    @PUT("score/reset")
    fun resetStatsForUser(@Header("Authorization") token: String,@Body scoresEntry: ScoresEntry):Call<ResponseBody>

    @POST("mailinglist")
    fun addUserToMailingList(@Header("Authorization") token: String,@Body mailingList: Mailing_list): Call<ResponseBody>

    @DELETE("mailinglist/{username}")
    fun deleteUserFromMailingList(@Header("Authorization") token: String,@Path("username") username: String): Call<ResponseBody>

    @GET("api/user/{username}")
    fun getEmailForUsername(@Header("Authorization") token: String,@Path("username") username: String): Call<String>
}