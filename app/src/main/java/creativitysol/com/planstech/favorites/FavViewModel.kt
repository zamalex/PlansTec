package creativitysol.com.planstech.favorites

import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import creativitysol.com.planstech.api.Retrofit
import creativitysol.com.planstech.gladtoserve.model.Services
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavViewModel:ViewModel() {

    fun getFavs(token: String,type:String) {

        Retrofit.Api.getFavs(token,type).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
               // services.value = response.body()
            }
        })
    }


    fun addFav(token: String,jsonObject: JsonObject) {

        Retrofit.Api.addFav(token,jsonObject).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                // services.value = response.body()
            }
        })
    }
}