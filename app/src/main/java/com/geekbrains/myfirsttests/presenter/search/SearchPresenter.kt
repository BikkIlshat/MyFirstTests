package com.geekbrains.myfirsttests.presenter.search

import com.geekbrains.myfirsttests.repository.GitHubRepository
import com.geekbrains.myfirsttests.view.search.ViewSearchContract
import com.geekbrains.myfirsttests.model.SearchResponse
import com.geekbrains.myfirsttests.presenter.RepositoryContract
import com.geekbrains.myfirsttests.repository.RepositoryCallback
import com.geekbrains.myfirsttests.utils.SchedulerProvider
import com.geekbrains.myfirsttests.utils.SearchSchedulerProvider
import com.geekbrains.myfirsttests.view.ViewContract
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
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
  private val repository: RepositoryContract,
  private val appSchedulerProvider: SchedulerProvider = SearchSchedulerProvider()
) : PresenterSearchContract, RepositoryCallback {

  override fun searchGitHub(searchQuery: String) {
    //Dispose
    val compositeDisposable = CompositeDisposable()
    compositeDisposable.add(
      repository.searchGithub(searchQuery)
        .subscribeOn(appSchedulerProvider.io())
        .observeOn(appSchedulerProvider.ui())
        .doOnSubscribe { viewContract.displayLoading(true) }
        .doOnTerminate { viewContract.displayLoading(false) }
        .subscribeWith(object : DisposableObserver<SearchResponse>() {

          override fun onNext(searchResponse: SearchResponse) {
            val searchResults = searchResponse.searchResults
            val totalCount = searchResponse.totalCount
            if (searchResults != null && totalCount != null) {
              viewContract.displaySearchResults(
                searchResults,
                totalCount
              )
            } else {
              viewContract.displayError("Search results or total count are null")
            }
          }

          override fun onError(e: Throwable) {
            viewContract.displayError(e.message ?: "Response is null or unsuccessful")
          }

          override fun onComplete() {}
        }
        )
    )
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
