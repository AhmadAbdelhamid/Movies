package com.example.moviestask.common

import android.app.Application
import com.facebook.stetho.Stetho
import timber.log.Timber

class AppActivity : Application() {
    override fun onCreate() {
        super.onCreate()

            Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(this)

    }
}