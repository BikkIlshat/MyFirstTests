package com.geekbrains.myfirsttests

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.myfirsttests.view.search.MainActivity
import junit.framework.TestCase
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {
  private lateinit var scenario: ActivityScenario<MainActivity>

  @Before
  fun setup() {
    scenario = ActivityScenario.launch(MainActivity::class.java)
  }

  @Test
  fun activitySearch_IsWorking() {
    onView(withId(R.id.searchEditText)).perform(click())
    onView(withId(R.id.searchEditText)).perform(replaceText("algol"), closeSoftKeyboard())
    onView(withId(R.id.searchEditText)).perform(pressImeActionButton())
    onView(isRoot()).perform(delay())
    onView(withId(R.id.totalCountTextView)).check(matches(withText("Number of results: 2897")))
  }

  private fun delay(): ViewAction {
    return object : ViewAction {
      override fun getConstraints(): Matcher<View> = isRoot()
      override fun getDescription(): String = "wait for $6 seconds"
      override fun perform(uiController: UiController, v: View?) {
        uiController.loopMainThreadForAtLeast(6000)
      }
    }
  }

  @After
  fun close() {
    scenario.close()
  }
}