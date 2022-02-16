package com.geekbrains.myfirsttests.presenter.search

import com.geekbrains.myfirsttests.presenter.PresenterContract

internal interface PresenterSearchContract : PresenterContract {
  fun searchGitHub(searchQuery: String)
  //onAttach
  //onDetach
}
