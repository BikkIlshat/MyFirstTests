package com.geekbrains.myfirsttests.presenter.details

import com.geekbrains.myfirsttests.view.ViewContract
import com.geekbrains.myfirsttests.view.details.ViewDetailsContract

internal class DetailsPresenter internal constructor(
  private val viewContract: ViewDetailsContract,
  private var count: Int = 0,
) : PresenterDetailsContract {
  private var view: ViewContract? = null
  override fun setCounter(count: Int) {
    this.count = count
  }

  override fun onIncrement() {
    count++
    viewContract.setCount(count)
  }

  override fun onDecrement() {
    count--
    viewContract.setCount(count)
  }

  override fun onAttach(view: ViewContract) {
    if (this.view == null && this.view != view) {
      this.view = view
    }
  }

  override fun onDetach() {
    view = null
  }
}
