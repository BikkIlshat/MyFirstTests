package com.geekbrains.myfirsttests.view.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.myfirsttests.R
import com.geekbrains.myfirsttests.model.SearchResult
import com.geekbrains.myfirsttests.presenter.RepositoryContract
import com.geekbrains.myfirsttests.presenter.search.PresenterSearchContract
import com.geekbrains.myfirsttests.presenter.search.SearchPresenter
import com.geekbrains.myfirsttests.repository.GitHubApi
import com.geekbrains.myfirsttests.repository.GitHubRepository
import com.geekbrains.myfirsttests.view.details.DetailsActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity(), ViewSearchContract {

  private val adapter = SearchResultAdapter()
  private val viewModel: SearchViewModel by lazy {
    ViewModelProvider(this).get(SearchViewModel::class.java)
  }
  private var totalCount: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setUI()
    viewModel.subscribeToLiveData().observe(this) { onStateChange(it) }

  }

  private fun onStateChange(screenState: ScreenState) {
    when (screenState) {
      is ScreenState.Working -> {
        val searchResponse = screenState.searchResponse
        val totalCount = searchResponse.totalCount
        progressBar.visibility = View.GONE
        with(totalCountTextView) {
          visibility = View.VISIBLE
          text =
            String.format(
              Locale.getDefault(),
              getString(R.string.results_count),
              totalCount
            )
        }

        this.totalCount = totalCount!!
        adapter.updateResults(searchResponse.searchResults!!)
      }
      is ScreenState.Loading -> {
        progressBar.visibility = View.VISIBLE
      }
      is ScreenState.Error -> {
        progressBar.visibility = View.GONE
        Toast.makeText(this, screenState.error.message, Toast.LENGTH_SHORT).show()
      }
    }
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
          viewModel.searchGitHub(query)
          return@OnEditorActionListener true
        } else {
          Toast.makeText(
            this@MainActivity,
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
    return GitHubRepository(createRetrofit().create(GitHubApi::class.java))
  }

  private fun createRetrofit(): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  override fun displaySearchResults(
    searchResults: List<SearchResult>,
    totalCount: Int,
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
