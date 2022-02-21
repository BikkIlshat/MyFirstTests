package com.geekbrains.myfirsttests

import com.geekbrains.myfirsttests.presenter.details.DetailsPresenter
import com.geekbrains.myfirsttests.view.details.ViewDetailsContract
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailsPresenterTests {
  private lateinit var presenter: DetailsPresenter
  private  lateinit var viewDetailsContract : CharSequence

  @Mock
  private lateinit var viewContract: ViewDetailsContract


  @Before
  fun setUp() {
    MockitoAnnotations.openMocks(this)
    presenter = DetailsPresenter(viewContract)
    viewDetailsContract  = viewContract
      .javaClass
      .simpleName
      .subSequence(0, "ViewDetailsContract".length)
  }

  @Test
  fun onIncrementPresenterTest() {
    presenter.onIncrement()
    verify(viewContract, atLeastOnce()).setCount(1)
  }

  @Test
  fun onDecrementPresenterTest() {
    presenter.onDecrement()
    verify(viewContract, atLeastOnce()).setCount(-1)
  }


  @Test
  fun attachView_PresenterTest() {
    presenter.onAttach(viewContract)
    val instance = presenter.javaClass
    instance.declaredFields.forEach {
      it.isAccessible = true
      if (it.name == "view") {
        Assert.assertEquals(viewContract, it.get(presenter))
      }
    }
  }

  @Test
  fun detachView_Presenter_Test() {
    presenter.onDetach()
    val instance = presenter.javaClass
    instance.declaredFields.forEach {
      it.isAccessible = true
      if (it.name == "view"){
        Assert.assertNull(it.get(presenter))
      }
    }
  }

}