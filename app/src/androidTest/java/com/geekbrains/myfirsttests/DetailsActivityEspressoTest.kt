package com.geekbrains.myfirsttests

import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.myfirsttests.view.details.DetailsActivity
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsActivityEspressoTest {

  private lateinit var scenario: ActivityScenario<DetailsActivity>

  @Before
  fun setup() {
    scenario = ActivityScenario.launch(DetailsActivity::class.java)
  }

  @Test
  fun activity_AssertNotNull() {
    scenario.onActivity {
      TestCase.assertNotNull(it)
    }
  }

  @Test
  fun activity_IsResumed() {
    TestCase.assertEquals(Lifecycle.State.RESUMED, scenario.state)
  }

  @Test
  fun activityTextView_NotNull() {
    scenario.onActivity {
      val totalCountTextView = it.findViewById<TextView>(R.id.totalCountTextView)
      TestCase.assertNotNull(totalCountTextView)
    }
  }

  @Test
  fun activityTextView_HasText() {
    val assertion = matches(withText("Number of results: 0"))
    onView(withId(R.id.totalCountTextView)).check(assertion)
  }

  @Test
  fun activityTextView_IsDisplayed() {
    onView(withId(R.id.totalCountTextView)).check(matches(ViewMatchers.isDisplayed()))
  }

  @Test
  fun activityTextView_IsCompletelyDisplayed() {
    onView(withId(R.id.totalCountTextView)).check(matches(ViewMatchers.isCompletelyDisplayed()))
  }

  @Test
  fun activityButtons_AreEffectiveVisible() {
    onView(withId(R.id.incrementButton)).check(matches(ViewMatchers.withEffectiveVisibility(
      ViewMatchers.Visibility.VISIBLE)))
    onView(withId(R.id.decrementButton)).check(matches(ViewMatchers.withEffectiveVisibility(
      ViewMatchers.Visibility.VISIBLE)))
  }

  @Test
  fun activityButtonIncrement_IsWorking() {
    onView(withId(R.id.incrementButton)).perform(ViewActions.click())
    onView(withId(R.id.totalCountTextView)).check(matches(withText("Number of results: 1")))
  }

  @Test
  fun activityButtonDecrement_IsWorking() {
    onView(withId(R.id.decrementButton)).perform(ViewActions.click())
    onView(withId(R.id.totalCountTextView)).check(matches(withText("Number of results: -1")))
  }

  @After
  fun close() {
    scenario.close()
  }
}
