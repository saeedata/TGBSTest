package org.technical.android

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.facebook.stetho.Stetho
import com.tgbs.test.BuildConfig
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import org.technical.android.di.components.DaggerAppComponent
import timber.log.Timber


class App : DaggerApplication() {


    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        initStetho()

        initTimber()

    }


    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(
                Timber.DebugTree()
            )
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerAppComponent.builder().application(this.applicationContext).build()
        component.inject(this)
        return component
    }

}