package creativitysol.com.planstech.main

import io.paperdb.Paper

class MyApp : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        Paper.init(this)

    }
}