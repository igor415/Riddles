package com.varivoda.igor.zagonetke.espresso.logintransition

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.varivoda.igor.zagonetke.R
import com.varivoda.igor.zagonetke.ui.loginActivity.MainActivity
import com.varivoda.igor.zagonetke.ui.navigation_activity.NavigationActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class LogIn {


    @get: Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun logIn() {
        Intents.init()
        onView(withId(R.id.usernameInput)).perform(typeText("test"), closeSoftKeyboard())
        onView(withId(R.id.passwordInput)).perform(typeText("test"), closeSoftKeyboard())
        onView(withId(R.id.buttonLogIn)).perform(click())
        intended(hasComponent(NavigationActivity::class.java.name))
        Intents.release()
    }
}