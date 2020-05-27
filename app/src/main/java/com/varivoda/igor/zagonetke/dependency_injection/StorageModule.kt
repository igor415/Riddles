package com.varivoda.igor.zagonetke.dependency_injection

import android.content.Context
import android.content.SharedPreferences
import com.varivoda.igor.zagonetke.ui.shared.Preferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storageModule = module {

    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            "PREF_NAME",
            Context.MODE_PRIVATE
        )
    }
    single { Preferences(get()) }
}