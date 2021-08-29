package club.infolab.itmo_lock

import android.app.Application
import club.infolab.itmo_lock.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ItmoLockApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ItmoLockApplication)
            modules(listOf(appModule, networkModule))
        }
    }
}
