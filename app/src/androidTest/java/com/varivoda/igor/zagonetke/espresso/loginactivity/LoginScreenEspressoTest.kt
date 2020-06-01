package com.varivoda.igor.zagonetke.espresso.loginactivity

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.varivoda.igor.zagonetke.R
import com.varivoda.igor.zagonetke.espresso.ToastMatcher
import com.varivoda.igor.zagonetke.ui.loginActivity.MainActivity
import com.varivoda.igor.zagonetke.ui.navigation_activity.NavigationActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenEspressoTest() {


    @JvmField
    @Rule
    var mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testEmptyFieldOfLoginForm(){
        Espresso.onView(withId(R.id.usernameInput)).perform(typeText("testing"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.buttonLogIn)).perform(click())
        Espresso.onView(withText(R.string.did_not_entered_login_info)).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))

    }

    @Test
    fun testWrongCredentials(){
        Espresso.onView(withId(R.id.usernameInput)).perform(typeText("testing"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.passwordInput)).perform(typeText("test"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.buttonLogIn)).perform(click())
        Espresso.onView(withText(R.string.username_or_password_not_correct)).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))
    }

    @Test
    fun testLoginSuccessful(){
        Intents.init()
        Espresso.onView(withId(R.id.usernameInput)).perform(typeText("test"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.passwordInput)).perform(typeText("test"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.buttonLogIn)).perform(click())
        intended(hasComponent(NavigationActivity::class.java.name))
        log_out()
        Intents.release()
    }



    private fun log_out(){
        Espresso.onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        Espresso.onView(withId(R.id.drawer_layout)).check(matches(isOpen()))
        Espresso.onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.log_out))
        Espresso.onView(withText(R.string.yes_resource)).perform(click())
    }

}