package creativitysol.com.planstech.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import creativitysol.com.planstech.api.Retrofit
import creativitysol.com.planstech.register.model.RegisterModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    var result: MutableLiveData<RegisterModel> = MutableLiveData()

    fun register(body: JsonObject) {
        Retrofit.Api.register(body).enqueue(object : Callback<RegisterModel> {
            override fun onFailure(call: Call<RegisterModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<RegisterModel>, response: Response<RegisterModel>) {
                if (response.isSuccessful) {
                    result.value = response.body()

                } else {

                }
            }
        })
    }

}