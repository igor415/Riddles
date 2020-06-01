package com.varivoda.igor.zagonetke.espresso.loginactivity

import androidx.test.espresso.Espresso
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
class RegisterScreenEspressoTest {

    @JvmField
    @Rule
    var mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testEmptyRegisterForm(){
        openFragment()
        Espresso.onView(withId(R.id.buttonLogInNew)).perform(scrollTo()).perform(click())
        Espresso.onView(withText(R.string.did_not_entered_login_info)).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))
    }

    @Test
    fun testForPasswordDemands(){
        openFragment()
        Espresso.onView(withId(R.id.usernameInputNew)).perform(typeText("lucija"))
        Espresso.onView(withId(R.id.firstNameInputNew)).perform(typeText("lucija"))
        Espresso.onView(withId(R.id.lastNameInputNew)).perform(typeText("lucija"))
        Espresso.onView(withId(R.id.petNameInputNew)).perform(typeText("rex"))
        Espresso.onView(withId(R.id.emailInputNew)).perform(typeText("lucija@gmail.com"))
        Espresso.onView(withId(R.id.passwordInputNew)).perform(typeText("a"))
        Espresso.onView(withId(R.id.buttonLogInNew)).perform(scrollTo()).perform(click())
        Espresso.onView(withText(R.string.password_demands)).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))
    }

    @Test
    fun testForEmailDemands(){
        openFragment()
        Espresso.onView(withId(R.id.usernameInputNew)).perform(typeText("lucija"))
        Espresso.onView(withId(R.id.firstNameInputNew)).perform(typeText("lucija"))
        Espresso.onView(withId(R.id.lastNameInputNew)).perform(typeText("lucija"))
        Espresso.onView(withId(R.id.petNameInputNew)).perform(typeText("rex"))
        Espresso.onView(withId(R.id.emailInputNew)).perform(typeText("a"))
        Espresso.onView(withId(R.id.passwordInputNew)).perform(typeText("pass1234"))
        Espresso.onView(withId(R.id.buttonLogInNew)).perform(scrollTo()).perform(click())
        Espresso.onView(withText(R.string.valid_email)).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))
    }

    @Test
    fun testForUsernameTaken(){
        openFragment()
        Espresso.onView(withId(R.id.usernameInputNew)).perform(typeText("test"))
        Espresso.onView(withId(R.id.firstNameInputNew)).perform(typeText("lucija"))
        Espresso.onView(withId(R.id.lastNameInputNew)).perform(typeText("lucija"))
        Espresso.onView(withId(R.id.petNameInputNew)).perform(typeText("rex"))
        Espresso.onView(withId(R.id.emailInputNew)).perform(typeText("a"))
        Espresso.onView(withId(R.id.passwordInputNew)).perform(typeText("pass1234"))
        Espresso.onView(withId(R.id.buttonLogInNew)).perform(scrollTo()).perform(click())
        Espresso.onView(withText(R.string.valid_email)).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))
    }

    @Test
    fun testForSuccessfulRegistration(){
        openFragment()
        Espresso.onView(withId(R.id.usernameInputNew)).perform(typeText("lucija"))
        Espresso.onView(withId(R.id.firstNameInputNew)).perform(typeText("lucija"))
        Espresso.onView(withId(R.id.lastNameInputNew)).perform(typeText("lucija"))
        Espresso.onView(withId(R.id.petNameInputNew)).perform(typeText("rex"))
        Espresso.onView(withId(R.id.emailInputNew)).perform(typeText("a@gmail.com"))
        Espresso.onView(withId(R.id.passwordInputNew)).perform(typeText("pass1234"))
        Espresso.onView(withId(R.id.buttonLogInNew)).perform(scrollTo()).perform(click())
        Espresso.onView(withText(R.string.registration_success)).inRoot(ToastMatcher()).check(matches(
            isDisplayed()))
    }


    private fun openFragment(){
        Espresso.onView(withId(R.id.newAccountText)).perform(click())
    }
}