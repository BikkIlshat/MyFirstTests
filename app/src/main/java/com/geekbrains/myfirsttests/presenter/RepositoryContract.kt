package com.geekbrains.myfirsttests.presenter

import com.geekbrains.myfirsttests.model.SearchResponse
import com.geekbrains.myfirsttests.repository.RepositoryCallback
import io.reactivex.Observable

interface RepositoryContract {
  fun searchGithub(
    query: String,
    callback: RepositoryCallback
  )

  fun searchGithub(
    query: String
  ): Observable<SearchResponse>
}