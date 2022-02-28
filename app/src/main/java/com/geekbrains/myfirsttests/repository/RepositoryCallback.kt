package com.geekbrains.myfirsttests.repository

import com.geekbrains.myfirsttests.model.SearchResponse
import retrofit2.Response

interface RepositoryCallback {
  fun handleGitHubResponse(response: Response<SearchResponse?>?)
  fun handleGitHubError()
}