package creativitysol.com.planstech.gladtoserve

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import creativitysol.com.planstech.api.Retrofit
import creativitysol.com.planstech.gladtoserve.model.Services
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactViewModel : ViewModel() {

    var services = MutableLiveData<Services>()
    fun send(jsonObject: JsonObject, token: String) {

        Retrofit.Api.leaveMessage(jsonObject, token).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            }
        })
    }


    fun getServices(token: String) {

        Retrofit.Api.getServices(token).enqueue(object : Callback<Services> {
            override fun onFailure(call: Call<Services>, t: Throwable) {

            }

            override fun onResponse(call: Call<Services>, response: Response<Services>) {
                services.value = response.body()
            }
        })
    }
}