package com.geekbrains.myfirsttests


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.geekbrains.myfirsttest.TEST_NUMBER_OF_RESULTS_MINUS_1
import com.geekbrains.myfirsttest.TEST_NUMBER_OF_RESULTS_PLUS_1
import com.geekbrains.myfirsttest.TEST_NUMBER_OF_RESULTS_ZERO
import com.geekbrains.myfirsttests.view.search.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class EspressoGeneratedTest {

  @Rule
  @JvmField
  var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

  @Test
  fun espressoGeneratedTest() {
    val materialButton = onView(
      allOf(withId(R.id.toDetailsActivityButton), withText("to details"),
        childAtPosition(
          childAtPosition(
            withId(android.R.id.content),
            0),
          3),
        isDisplayed()))
    materialButton.perform(click())

    val textView = onView(
      allOf(withId(R.id.totalCountTextView), withText(TEST_NUMBER_OF_RESULTS_ZERO),
        withParent(withParent(withId(android.R.id.content))),
        isDisplayed()))
    textView.check(matches(withText("Number of results: 0")))

    val textView2 = onView(
      allOf(withId(R.id.totalCountTextView), withText(TEST_NUMBER_OF_RESULTS_ZERO),
        withParent(withParent(withId(android.R.id.content))),
        isDisplayed()))
    textView2.check(matches(withText(TEST_NUMBER_OF_RESULTS_ZERO)))

    val materialButton2 = onView(
      allOf(withId(R.id.incrementButton), withText("+"),
        childAtPosition(
          childAtPosition(
            withId(android.R.id.content),
            0),
          2),
        isDisplayed()))
    materialButton2.perform(click())

    val textView3 = onView(
      allOf(withId(R.id.totalCountTextView), withText(TEST_NUMBER_OF_RESULTS_PLUS_1),
        withParent(withParent(withId(android.R.id.content))),
        isDisplayed()))
    textView3.check(matches(withText(TEST_NUMBER_OF_RESULTS_PLUS_1)))

    val materialButton3 = onView(
      allOf(withId(R.id.decrementButton), withText("-"),
        childAtPosition(
          childAtPosition(
            withId(android.R.id.content),
            0),
          0),
        isDisplayed()))
    materialButton3.perform(click())

    val materialButton4 = onView(
      allOf(withId(R.id.decrementButton), withText("-"),
        childAtPosition(
          childAtPosition(
            withId(android.R.id.content),
            0),
          0),
        isDisplayed()))
    materialButton4.perform(click())

    val textView4 = onView(
      allOf(withId(R.id.totalCountTextView), withText(TEST_NUMBER_OF_RESULTS_MINUS_1),
        withParent(withParent(withId(android.R.id.content))),
        isDisplayed()))
    textView4.check(matches(withText(TEST_NUMBER_OF_RESULTS_MINUS_1)))
  }

  private fun childAtPosition(
    parentMatcher: Matcher<View>, position: Int,
  ): Matcher<View> {

    return object : TypeSafeMatcher<View>() {
      override fun describeTo(description: Description) {
        description.appendText("Child at position $position in parent ")
        parentMatcher.describeTo(description)
      }

      public override fun matchesSafely(view: View): Boolean {
        val parent = view.parent
        return parent is ViewGroup && parentMatcher.matches(parent)
          && view == parent.getChildAt(position)
      }
    }
  }
}
