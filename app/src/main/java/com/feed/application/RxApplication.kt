package com.feed.application

import android.app.Application

/**
 * Created by Barak Halevi on 07/11/2018.
 */
class RxApplication : Application() {
    var networkService: NetworkService? = null
        private set


    override fun onCreate() {
        super.onCreate()
        instance_ = this
        networkService = NetworkService()
    }

    companion object {
        @get:Synchronized
        var instance_: RxApplication? = null
            private set
    }
}