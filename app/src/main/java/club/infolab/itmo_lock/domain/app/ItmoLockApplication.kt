package club.infolab.itmo_lock.domain.app

import android.app.Application
import club.infolab.itmo_lock.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ItmoLockApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ItmoLockApplication)
            modules(appModule)
        }
    }
}