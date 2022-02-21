package com.geekbrains.myfirsttests.presenter.details

import com.geekbrains.myfirsttests.presenter.PresenterContract

internal interface PresenterDetailsContract : PresenterContract {
    fun setCounter(count: Int)
    fun onIncrement()
    fun onDecrement()
}
