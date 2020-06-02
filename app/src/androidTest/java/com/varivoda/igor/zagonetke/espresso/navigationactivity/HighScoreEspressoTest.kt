package com.varivoda.igor.zagonetke.espresso.navigationactivity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.varivoda.igor.zagonetke.R
import com.varivoda.igor.zagonetke.ui.navigation_activity.NavigationActivity
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HighScoreEspressoTest {

    @get: Rule
    val activityRule = ActivityScenarioRule(NavigationActivity::class.java)

    @Test
    fun testImageIconVisibility(){
        openFragment()
        onView(allOf(withContentDescription(R.string.image_description),isDisplayed()))
    }

    private fun openFragment(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isOpen()))
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_scores))
    }


}