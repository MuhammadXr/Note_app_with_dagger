package uz.gita.noteapp_by_xr.app

import android.app.Application
import androidx.viewbinding.BuildConfig
import timber.log.Timber
import uz.gita.noteapp_by_xr.data.source.local.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppDatabase.init(this)
    }
}