package com.feed.application

import android.app.Application



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