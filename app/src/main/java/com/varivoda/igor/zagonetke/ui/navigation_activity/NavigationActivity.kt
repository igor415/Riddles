package com.varivoda.igor.zagonetke.ui.navigation_activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.varivoda.igor.zagonetke.R
import com.varivoda.igor.zagonetke.receivers.ID
import com.varivoda.igor.zagonetke.receivers.NotificationReceiver
import com.varivoda.igor.zagonetke.settings.SettingsViewModel
import com.varivoda.igor.zagonetke.ui.shared.Preferences
import com.varivoda.igor.zagonetke.ui.shared.Utils
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class NavigationActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val preferences : Preferences by inject()
    private val settingsViewModel by viewModel<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_levels,R.id.nav_scores,R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val headerView = navigationView.getHeaderView(0)
        val txtUsername = headerView.findViewById<TextView>(R.id.usernameTitle)
        txtUsername.text = preferences.getUser()

        settingsViewModel.getEmailForUser(preferences.getToken(),preferences.getUser())
        settingsViewModel.email.observe(this, androidx.lifecycle.Observer {
            if(it!=null){
                preferences.setCurrentUserEmail(it)
            }
        })

        showNotification()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun logOutDialog() {
        Utils.showDefaultDialog(this,getString(R.string.log_out_question),negativeButtonClick, positiveButtonClick)
    }
    private val negativeButtonClick = { _: DialogInterface, _: Int ->
    }
    private val positiveButtonClick = { _: DialogInterface, _: Int ->
        preferences.clear("username key")
        finish()
    }

    fun logOutMenuClick(item: MenuItem) {
        logOutDialog()
    }

    private fun showNotification(){
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,18)
        calendar.set(Calendar.MINUTE,52)
        val intent = Intent(applicationContext,NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, ID,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent)
    }


}
