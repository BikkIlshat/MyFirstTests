package com.geekbrains.myfirsttests.presenter

import com.geekbrains.myfirsttests.view.ViewContract

internal interface PresenterContract {
  fun onAttach(view : ViewContract)
  fun onDetach()
}
