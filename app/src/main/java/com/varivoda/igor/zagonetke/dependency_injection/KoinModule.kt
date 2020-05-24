package com.varivoda.igor.zagonetke.dependency_injection

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.varivoda.igor.zagonetke.data.ApiService
import com.varivoda.igor.zagonetke.data.Repository
import com.varivoda.igor.zagonetke.settings.SettingsViewModel
import com.varivoda.igor.zagonetke.ui.loginActivity.LoginViewModel
import com.varivoda.igor.zagonetke.ui.navigation_activity.ui.highscore.HighScoreViewModel
import com.varivoda.igor.zagonetke.ui.navigation_activity.ui.levels.LevelsViewModel
import com.varivoda.igor.zagonetke.ui.navigation_activity.ui.riddle.RiddleViewModel
import com.varivoda.igor.zagonetke.ui.shared.Preferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://192.168.0.108:8080"

val apiModule = module {
    fun provideApi(retrofit: Retrofit): ApiService{
        return retrofit.create(ApiService::class.java)
    }
    single { provideApi(get()) }
}
val repositoryModule = module {
    single {
        Repository(get())
    }
}
val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { LevelsViewModel(get()) }
    viewModel { RiddleViewModel(get()) }
    viewModel { HighScoreViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}
val retrofitModule = module {
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor(logging)
        return okHttpClientBuilder.build()
    }
    fun provideGson(): Gson{
        return GsonBuilder().setLenient().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }
    fun provideRetrofit(gson: Gson): Retrofit{
        return Retrofit.Builder().baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create(gson)).build()
    }
    single { provideHttpClient() }
    single { provideGson() }
    single { provideRetrofit(get()) }
}

val preferencesModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            "PREF_NAME",
            Context.MODE_PRIVATE
        )
    }
    single { Preferences(get()) }
}
