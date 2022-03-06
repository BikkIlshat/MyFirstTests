package com.geekbrains.myfirsttests.repository

import com.geekbrains.myfirsttests.model.SearchResponse
import com.geekbrains.myfirsttests.presenter.RepositoryContract
import retrofit2.Response

internal class FakeGitHubRepository : RepositoryContract {
  override fun searchGithub(
    query: String,
    callback: RepositoryCallback,
  ) {
    callback.handleGitHubResponse(Response.success(SearchResponse(42, listOf())))
  }
}