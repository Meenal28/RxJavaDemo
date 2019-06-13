package com.app.demo.rx.utils

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppRxSchedulers {
    companion object {
        var NETWORK_EXECUTOR: Executor = Executors.newCachedThreadPool()
        var NETWORK_SCHEDULER: Scheduler = Schedulers.from(NETWORK_EXECUTOR);
        var BACKGROUND_EXECUTOR: Executor = Executors.newCachedThreadPool();
        var BACKGROUND_SCHEDULER: Scheduler = Schedulers.from(BACKGROUND_EXECUTOR);
    }



}