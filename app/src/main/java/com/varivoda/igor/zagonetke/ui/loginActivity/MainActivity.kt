package com.varivoda.igor.zagonetke.ui.loginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.varivoda.igor.zagonetke.R
import com.varivoda.igor.zagonetke.ui.navigation_activity.NavigationActivity
import com.varivoda.igor.zagonetke.ui.shared.Preferences
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val preferences : Preferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        if(preferences.getUser()!="null"){
            startActivity(Intent(this,NavigationActivity::class.java))
            finish()
        }
        setTheme(R.style.loginActivityStyle)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
