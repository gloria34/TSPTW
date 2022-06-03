package dnu.fpm.tsptw

import android.app.Application
import dnu.fpm.tsptw.data.local.PreferencesHelper

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        PreferencesHelper.loadPreferencesHelper(this, packageName)
    }
}