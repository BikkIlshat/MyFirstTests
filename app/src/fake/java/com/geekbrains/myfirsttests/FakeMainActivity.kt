package com.geekbrains.myfirsttests

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.myfirsttests.model.SearchResult
import com.geekbrains.myfirsttests.presenter.RepositoryContract
import com.geekbrains.myfirsttests.presenter.search.PresenterSearchContract
import com.geekbrains.myfirsttests.presenter.search.SearchPresenter
import com.geekbrains.myfirsttests.repository.FakeGitHubRepository
import com.geekbrains.myfirsttests.view.details.DetailsActivity
import com.geekbrains.myfirsttests.view.search.SearchResultAdapter
import com.geekbrains.myfirsttests.view.search.ViewSearchContract
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class FakeMainActivity : AppCompatActivity(), ViewSearchContract {

  private val adapter = SearchResultAdapter()
  private val presenter: PresenterSearchContract = SearchPresenter(this, createRepository())
  private var totalCount: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_fake_main)
    setUI()
  }

  private fun setUI() {
    findViewById<Button>(R.id.toDetailsActivityButton).setOnClickListener {
      startActivity(DetailsActivity.getIntent(this, totalCount))
    }
    setQueryListener()
    setRecyclerView()
  }

  private fun setRecyclerView() {
    findViewById<RecyclerView>(R.id.recyclerView).setHasFixedSize(true)
    findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
  }

  private fun setQueryListener() {
    findViewById<EditText>(R.id.searchEditText).setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
      if (actionId == EditorInfo.IME_ACTION_SEARCH) {
        val query = findViewById<EditText>(R.id.searchEditText).text.toString()
        if (query.isNotBlank()) {
          presenter.searchGitHub(query)
          return@OnEditorActionListener true
        } else {
          Toast.makeText(
            this@FakeMainActivity,
            getString(R.string.enter_search_word),
            Toast.LENGTH_SHORT
          ).show()
          return@OnEditorActionListener false
        }
      }
      false
    })
  }

  private fun createRepository(): RepositoryContract {
    return  FakeGitHubRepository()
  }

  private fun createRetrofit(): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  override fun displaySearchResults(
    searchResults: List<SearchResult>,
    totalCount: Int
  ) {
    with(findViewById<TextView>(R.id.totalCountTextView)) {
      visibility = View.VISIBLE
      text = String.format(Locale.getDefault(), getString(R.string.results_count), totalCount)
    }

    this.totalCount = totalCount
    adapter.updateResults(searchResults)
  }

  override fun displayError() {
    Toast.makeText(this, getString(R.string.undefined_error), Toast.LENGTH_SHORT).show()
  }

  override fun displayError(error: String) {
    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
  }

  override fun displayLoading(show: Boolean) {
    if (show) {
      findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
    } else {
      findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
    }
  }


  companion object {
    const val BASE_URL = "https://api.github.com"
    const val FAKE = "FAKE"
  }

}
