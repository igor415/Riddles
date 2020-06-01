package com.varivoda.igor.zagonetke.espresso.navigationactivity

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.varivoda.igor.zagonetke.R
import com.varivoda.igor.zagonetke.ui.navigation_activity.NavigationActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeFragmentEspressoTest {

    @JvmField
    @Rule
    var mActivityRule: ActivityTestRule<NavigationActivity> = ActivityTestRule(NavigationActivity::class.java)

    @Test
    fun testStartButton(){
        onView(withId(R.id.startButton)).perform(click())
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }
}