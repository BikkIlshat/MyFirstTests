package com.geekbrains.myfirsttests.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchSchedulerProvider : SchedulerProvider {

  override fun ui(): Scheduler = AndroidSchedulers.mainThread()

  override fun io(): Scheduler = Schedulers.io()

}