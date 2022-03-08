import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.myfirsttests.FakeMainActivity
import com.geekbrains.myfirsttests.R
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FakeMainActivityEspressoTest {

  private lateinit var scenario: ActivityScenario<FakeMainActivity>

  @Before
  fun setup() {
    scenario = ActivityScenario.launch(FakeMainActivity::class.java)
  }

  @Test
  fun activitySearch_IsWorking() {
    Espresso.onView(withId(R.id.searchEditText)).perform(ViewActions.click())
    Espresso.onView(withId(R.id.searchEditText))
      .perform(ViewActions.replaceText("algol"), ViewActions.closeSoftKeyboard())
    Espresso.onView(withId(R.id.searchEditText)).perform(ViewActions.pressImeActionButton())
    Espresso.onView(withId(R.id.totalCountTextView))
      .check(ViewAssertions.matches(ViewMatchers.withText("Number of results: 42")))
  }

  @After
  fun close() {
    scenario.close()
  }
}
