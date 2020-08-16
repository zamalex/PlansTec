package creativitysol.com.planstech.conschat

import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import creativitysol.com.planstech.api.Retrofit
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatViewModel:ViewModel() {

    fun getChats(token: String) {

        Retrofit.Api.getChats(token).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                //services.value = response.body()
            }
        })
    }

    fun sendChat(token: String,jsonObject: JsonObject) {

        Retrofit.Api.sendChat(token,jsonObject).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                //services.value = response.body()
            }
        })
    }
}