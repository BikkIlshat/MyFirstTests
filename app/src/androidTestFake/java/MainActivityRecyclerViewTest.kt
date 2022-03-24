import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4

import com.geekbrains.myfirsttests.FakeMainActivity
import com.geekbrains.myfirsttests.R

import com.geekbrains.myfirsttests.view.search.SearchResultAdapter
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityRecyclerViewTest {

  private lateinit var scenario: ActivityScenario<FakeMainActivity>

  @Before
  fun setup() {
    scenario = ActivityScenario.launch(FakeMainActivity::class.java)
  }

  @Test
  fun activitySearch_ScrollTo() {

    loadList()

    Espresso.onView(withId(R.id.recyclerView))
      .perform(
        RecyclerViewActions.scrollTo<SearchResultAdapter.SearchResultViewHolder>(
          ViewMatchers.hasDescendant(ViewMatchers.withText("FullName: 42"))
        )
      )

  }

  @Test
  fun activitySearch_PerformClickAtPosition() {

    loadList()

    Espresso.onView(withId(R.id.recyclerView))
      .perform(
        RecyclerViewActions.actionOnItemAtPosition<SearchResultAdapter.SearchResultViewHolder>(
          0,
          ViewActions.click()
        )
      )

  }

  @Test
  fun activitySearch_PerformClickOnItem() {

    loadList()

    Espresso.onView(withId(R.id.recyclerView))
      .perform(
        RecyclerViewActions.scrollTo<SearchResultAdapter.SearchResultViewHolder>(
          ViewMatchers.hasDescendant(ViewMatchers.withText("FullName: 50"))
        )
      )

    Espresso.onView(withId(R.id.recyclerView))
      .perform(
        RecyclerViewActions.actionOnItem<SearchResultAdapter.SearchResultViewHolder>(
          ViewMatchers.hasDescendant(ViewMatchers.withText("FullName: 42")),
          ViewActions.click()
        )
      )

  }

  @Test
  fun activitySearch_PerformCustomClick() {

    loadList()

    Espresso.onView(withId(R.id.recyclerView))
      .perform(
        RecyclerViewActions
          .actionOnItemAtPosition<SearchResultAdapter.SearchResultViewHolder>(
            0,
            tapOnItemWithId(R.id.checkbox)
          )
      )

  }

  private fun loadList() {
    Espresso.onView(withId(R.id.searchEditText)).perform(ViewActions.click())
    Espresso.onView(withId(R.id.searchEditText))
      .perform(ViewActions.replaceText("algol"), ViewActions.closeSoftKeyboard())
    Espresso.onView(withId(R.id.searchEditText)).perform(ViewActions.pressImeActionButton())
  }

  private fun tapOnItemWithId(id: Int) = object : ViewAction {
    override fun getConstraints(): Matcher<View>? {
      return null
    }

    override fun getDescription(): String {
      return "Нажимаем на view с указанным id"
    }

    override fun perform(uiController: UiController, view: View) {
      val v = view.findViewById(id) as View
      v.performClick()
    }
  }

  @After
  fun close() {
    scenario.close()
  }
}