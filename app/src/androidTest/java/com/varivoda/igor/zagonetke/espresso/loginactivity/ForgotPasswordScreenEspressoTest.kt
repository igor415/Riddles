package com.varivoda.igor.zagonetke.espresso.loginactivity

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.varivoda.igor.zagonetke.R
import com.varivoda.igor.zagonetke.espresso.ToastMatcher
import com.varivoda.igor.zagonetke.ui.loginActivity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ForgotPasswordScreenEspressoTest() {


    @JvmField
    @Rule
    var mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testEmptyFieldsInForm(){
        openFragment()
        Espresso.onView(withId(R.id.buttonLogInForgot)).perform(click())
        Espresso.onView(withText(R.string.did_not_entered_login_info)).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))
    }

    @Test
    fun testForPasswordDemands(){
        openFragment()
        Espresso.onView(withId(R.id.petNameForgot)).perform(
            typeText("tara"),
            closeSoftKeyboard()
        )
        Espresso.onView(withId(R.id.passwordInputForgot)).perform(typeText("sifra"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.userNameForgot)).perform(typeText("pero"))
        Espresso.onView(withId(R.id.buttonLogInForgot)).perform(click())
        Espresso.onView(withText(R.string.password_demands)).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))
    }

    @Test
    fun testForWrongCredentials(){
        openFragment()
        Espresso.onView(withId(R.id.petNameForgot)).perform(
            typeText("unknown"),
            closeSoftKeyboard()
        )
        Espresso.onView(withId(R.id.passwordInputForgot)).perform(typeText("sifra1234"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.userNameForgot)).perform(typeText("pero"))
        Espresso.onView(withId(R.id.buttonLogInForgot)).perform(click())
        Espresso.onView(withText(R.string.inserted_info_not_correct)).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))
    }

    @Test
    fun testForSuccessfulChange(){
        openFragment()
        Espresso.onView(withId(R.id.petNameForgot)).perform(
            typeText("tara"),
            closeSoftKeyboard()
        )
        Espresso.onView(withId(R.id.passwordInputForgot)).perform(typeText("sifra1234"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.userNameForgot)).perform(typeText("pero"))
        Espresso.onView(withId(R.id.buttonLogInForgot)).perform(click())
        Espresso.onView(withText(R.string.changed_password_success)).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))
    }


    private fun openFragment(){
        Espresso.onView(withId(R.id.forgotPasswordText)).perform(click())
    }
}