package com.geekbrains.myfirsttest

import com.geekbrains.myfirsttests.utils.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class ScheduleProviderStub : SchedulerProvider {
  override fun ui(): Scheduler = Schedulers.trampoline()

  override fun io(): Scheduler = Schedulers.trampoline()

}
