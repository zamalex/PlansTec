package creativitysol.com.planstech.base

import creativitysol.com.planstech.base.di.appModule
import creativitysol.com.planstech.password.di.forgotPassModule
import creativitysol.com.planstech.password.di.resetPassModule
import io.paperdb.Paper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApp : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        Paper.init(this)
        koinStart
    }

    private val koinStart = startKoin {
        androidLogger(Level.NONE)
        androidContext(this@BaseApp)
        modules(appModule, forgotPassModule, resetPassModule)
    }
}