package com.geekbrains.myfirsttests.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.myfirsttests.FakeMainActivity.Companion.BASE_URL
import com.geekbrains.myfirsttests.model.SearchResponse
import com.geekbrains.myfirsttests.presenter.RepositoryContract
import com.geekbrains.myfirsttests.repository.GitHubApi
import com.geekbrains.myfirsttests.repository.GitHubRepository
import com.geekbrains.myfirsttests.utils.SchedulerProvider
import com.geekbrains.myfirsttests.utils.SearchSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SearchViewModel(
  private val repository: RepositoryContract = GitHubRepository(
    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .build().create(GitHubApi::class.java)
  ),
  private val appSchedulerProvider: SchedulerProvider = SearchSchedulerProvider()
) : ViewModel() {

  private val _liveData = MutableLiveData<ScreenState>()
  private val liveData: LiveData<ScreenState> = _liveData

  fun subscribeToLiveData() = liveData

  fun searchGitHub(searchQuery: String) {
    //Dispose
    val compositeDisposable = CompositeDisposable()
    compositeDisposable.add(
      repository.searchGithub(searchQuery)
        .subscribeOn(appSchedulerProvider.io())
        .observeOn(appSchedulerProvider.ui())
        .doOnSubscribe { _liveData.value = ScreenState.Loading }
        .subscribeWith(object : DisposableObserver<SearchResponse>() {

          override fun onNext(searchResponse: SearchResponse) {
            val searchResults = searchResponse.searchResults
            val totalCount = searchResponse.totalCount
            if (searchResults != null && totalCount != null) {
              _liveData.value = ScreenState.Working(searchResponse)
            } else {
              _liveData.value =
                ScreenState.Error(Throwable("Search results or total count are null"))
            }
          }

          override fun onError(e: Throwable) {
            _liveData.value =
              ScreenState.Error(
                Throwable(
                  e.message ?: "Response is null or unsuccessful"
                )
              )
          }

          override fun onComplete() {}
        }
        )
    )
  }
}

sealed class ScreenState {
  object Loading : ScreenState()
  data class Working(val searchResponse: SearchResponse) : ScreenState()
  data class Error(val error: Throwable) : ScreenState()
}
