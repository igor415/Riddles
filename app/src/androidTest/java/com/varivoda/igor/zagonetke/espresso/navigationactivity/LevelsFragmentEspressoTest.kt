package com.varivoda.igor.zagonetke.espresso.navigationactivity

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.varivoda.igor.zagonetke.R
import com.varivoda.igor.zagonetke.ui.navigation_activity.NavigationActivity
import com.varivoda.igor.zagonetke.ui.navigation_activity.ui.levels.LevelAdapter
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LevelsFragmentEspressoTest {

    @JvmField
    @Rule
    var mActivityRule: ActivityTestRule<NavigationActivity> = ActivityTestRule(NavigationActivity::class.java)

    @Test
    fun testForClickOnItemEnabled(){
        openLevelFragment()
        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<LevelAdapter.MyViewHolder>(0, click()))
        onView(withId(R.id.textQuestion)).check(matches(isDisplayed()))
    }

    @Test
    fun testForClickOnItemDisabled(){
        openLevelFragment()
        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<LevelAdapter.MyViewHolder>(15, click()))
        onView(withId(R.id.textQuestion)).check(doesNotExist())
    }

    private fun openLevelFragment(){
        onView(withId(R.id.startButton)).perform(click())
    }
}