package com.example.jobplanettest.initializer

import android.content.Context
import androidx.startup.Initializer
import com.example.jobplanettest.BuildConfig
import com.example.jobplanettest.util.JobPlanetDebugTree
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(JobPlanetDebugTree())
            Timber.d("timber Initalized")
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
