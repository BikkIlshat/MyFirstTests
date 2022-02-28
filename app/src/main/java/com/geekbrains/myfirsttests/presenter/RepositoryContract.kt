package com.geekbrains.myfirsttests.presenter

import com.geekbrains.myfirsttests.repository.RepositoryCallback

internal interface RepositoryContract {
  fun searchGithub(
    query: String,
    callback: RepositoryCallback
  )
}