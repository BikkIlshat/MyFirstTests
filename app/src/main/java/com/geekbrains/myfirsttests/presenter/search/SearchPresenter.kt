package com.geekbrains.myfirsttests.presenter.search

import com.geekbrains.myfirsttests.repository.GitHubRepository
import com.geekbrains.myfirsttests.view.search.ViewSearchContract
import com.geekbrains.myfirsttests.model.SearchResponse
import com.geekbrains.myfirsttests.presenter.RepositoryContract
import com.geekbrains.myfirsttests.repository.RepositoryCallback
import com.geekbrains.myfirsttests.view.ViewContract
import retrofit2.Response

/**
 * В архитектуре MVP все запросы на получение данных адресуются в Репозиторий.
 * Запросы могут проходить через Interactor или UseCase, использовать источники
 * данных (DataSource), но суть от этого не меняется.
 * Непосредственно Презентер отвечает за управление потоками запросов и ответов,
 * выступая в роли регулировщика движения на перекрестке.
 */

internal class SearchPresenter internal constructor(
  private val viewContract: ViewSearchContract,
  private val repository: RepositoryContract
) : PresenterSearchContract, RepositoryCallback {

  private var view: ViewContract? = null

  override fun searchGitHub(searchQuery: String) {
    viewContract.displayLoading(true)
    repository.searchGithub(searchQuery, this)
  }

  override fun onAttach(view: ViewContract) {
    if (this.view == null && this.view != view) {
      this.view = view
    }
  }

  override fun onDetach() {
    view = null
  }

  override fun handleGitHubResponse(response: Response<SearchResponse?>?) {
    viewContract.displayLoading(false)
    if (response != null && response.isSuccessful) {
      val searchResponse = response.body()
      val searchResults = searchResponse?.searchResults
      val totalCount = searchResponse?.totalCount
      if (searchResults != null && totalCount != null) {
        viewContract.displaySearchResults(
          searchResults,
          totalCount
        )
      } else {
        viewContract.displayError("Search results or total count are null")
      }
    } else {
      viewContract.displayError("Response is null or unsuccessful")
    }
  }

  override fun handleGitHubError() {
    viewContract.displayLoading(false)
    viewContract.displayError()
  }
}
