package com.geekbrains.myfirsttests.view.search


import com.geekbrains.myfirsttests.model.SearchResult
import com.geekbrains.myfirsttests.view.ViewContract

internal interface ViewSearchContract : ViewContract {
  fun displaySearchResults(
    searchResults: List<SearchResult>,
    totalCount: Int
  )

  fun displayError()
  fun displayError(error: String)
  fun displayLoading(show: Boolean)
}
