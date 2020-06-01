package com.varivoda.igor.zagonetke.espresso.navigationactivity

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.varivoda.igor.zagonetke.R
import com.varivoda.igor.zagonetke.ui.navigation_activity.NavigationActivity
import com.varivoda.igor.zagonetke.ui.navigation_activity.ui.levels.LevelAdapter
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RiddleFragmentEspressoTest {

    @JvmField
    @Rule
    var mActivityRule: ActivityTestRule<NavigationActivity> = ActivityTestRule(NavigationActivity::class.java)

    @Test
    fun testWrongAnswer(){
        openRiddleFragment()
        onView(withId(R.id.confirmButton)).perform(click())
        onView(withText(R.string.wrong_answer_resource)).check(matches(isDisplayed()))
    }

    @Test
    fun testCorrectAnswer(){
        openRiddleFragment()
        "footsteps".forEach {
            onView(withText(it.toString())).perform(click())
        }
        onView(withId(R.id.confirmButton)).perform(click())
        onView(withText(R.string.correct_resource)).check(matches(isDisplayed()))
    }


    private fun <T> first(matcher: Matcher<T>): Matcher<T>? {
        return object : BaseMatcher<T>() {
            var isFirst = true
            override fun matches(item: Any): Boolean {
                if (isFirst && matcher.matches(item)) {
                    isFirst = false
                    return true
                }
                return false
            }

            override fun describeTo(description: Description) {
                description.appendText("should return first matching item")
            }
        }
    }

    private fun hasValueEqualTo(content: Int = 50): Matcher<View?>? {
        return object : TypeSafeMatcher<View?>() {
            override fun describeTo(description: Description) {
                description.appendText("Has EditText/TextView the value:  $content")
            }

            override fun matchesSafely(view: View?): Boolean {
                if (view !is TextView && view !is EditText) {
                    return false
                }
                val text: Int = if (view is TextView) {
                    view.text.toString().substring(7).toInt()
                } else {
                    (view as EditText).text.toString().toInt()
                }
                return text <= content
            }
        }
    }


    private fun openRiddleFragment(){
        onView(withId(R.id.startButton)).perform(click())
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<LevelAdapter.MyViewHolder>(0,click()))
    }
}