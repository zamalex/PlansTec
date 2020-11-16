package creativitysol.com.planstech.base

import addToFavouritesModule
import android.content.res.Configuration
import android.util.Log
import com.google.gson.JsonObject
import com.onesignal.OneSignal
import com.yariksoffice.lingver.Lingver
import creativitysol.com.planstech.api.Retrofit
import creativitysol.com.planstech.base.di.appModule
import creativitysol.com.planstech.conschat.di.chatModule
import creativitysol.com.planstech.consultation_request_questions.di.requestConsultationModule
import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.notifications.di.notificationsModule
import creativitysol.com.planstech.password.di.forgotPassModule
import creativitysol.com.planstech.password.di.resetPassModule
import io.paperdb.Paper
import listFavouritesModule
import okhttp3.ResponseBody
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import retrofit2.Call
import retrofit2.Response
import java.util.*

class BaseApp : android.app.Application() {
    var player_id: String? = null
    var device_token: String? = null

    val loginModel by lazy {
        Paper.book().read("login", LoginModel())
    }

    override fun onCreate() {
        super.onCreate()
        Lingver.init(this);
        Lingver.getInstance().setLocale(this, "ar");
        Paper.init(this)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization

        // OneSignal Initialization
        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)

            .init()

        /*  OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.d("debugone", "User:" + userId);
                if (registrationId != null)
                    Log.d("debugone", "registrationId:" + registrationId);

            }
        });

*/

        OneSignal.idsAvailable { userId, registrationId ->
            Log.d("iddd", userId)
            if (!loginModel.data.token.isEmpty()){
                var body:JsonObject = JsonObject().apply {
                    addProperty("player_id",userId)
                    addProperty("device_token",initToken())
                }
                Retrofit.Api.snedNotToken("Bearer ${loginModel.data.token}",body).enqueue(object : retrofit2.Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    }

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                    }
                })

            }
        }




        koinStart
    }

    private val koinStart = startKoin {
        androidLogger(Level.NONE)
        androidContext(this@BaseApp)
        modules(
            appModule,
            forgotPassModule,
            resetPassModule,
            addToFavouritesModule,
            listFavouritesModule,
            notificationsModule,
            requestConsultationModule,
            chatModule
        )
    }


    fun initToken():String{
        var mToken:String = Paper.book().read("device","")

        if(mToken.isEmpty()){
            mToken = UUID.randomUUID().toString()
            Paper.book().write("device",mToken)
        }
        return  mToken
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Lingver.getInstance().setLocale(this, "ar")

    }
}