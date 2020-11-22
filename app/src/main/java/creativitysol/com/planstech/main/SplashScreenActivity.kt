package creativitysol.com.planstech.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import creativitysol.com.planstech.R
import java.util.*

class SplashScreenActivity : AppCompatActivity() {


    lateinit var timer:Timer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        timer = Timer()




        timer.schedule(object :TimerTask(){
            override fun run() {
                startActivity(Intent(this@SplashScreenActivity,
                    MainActivity::class.java))
                this@SplashScreenActivity.finish()
            }
        },3000,10000)
    }

    override fun onDestroy() {
        super.onDestroy()

        timer.cancel()
    }
}
