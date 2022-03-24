package com.geekbrains.myfirsttests.utils

import io.reactivex.Scheduler

internal interface SchedulerProvider {
    fun ui(): Scheduler
    fun io(): Scheduler
}
