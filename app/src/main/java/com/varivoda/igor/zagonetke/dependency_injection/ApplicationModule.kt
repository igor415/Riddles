package com.varivoda.igor.zagonetke.dependency_injection

import com.varivoda.igor.zagonetke.data.Repository
import com.varivoda.igor.zagonetke.settings.SettingsViewModel
import com.varivoda.igor.zagonetke.ui.loginActivity.LoginViewModel
import com.varivoda.igor.zagonetke.ui.navigation_activity.ui.highscore.HighScoreViewModel
import com.varivoda.igor.zagonetke.ui.navigation_activity.ui.levels.LevelsViewModel
import com.varivoda.igor.zagonetke.ui.navigation_activity.ui.riddle.RiddleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    single {
        Repository(get())
    }

    viewModel { LoginViewModel(get()) }
    viewModel { LevelsViewModel(get()) }
    viewModel { RiddleViewModel(get()) }
    viewModel { HighScoreViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}



