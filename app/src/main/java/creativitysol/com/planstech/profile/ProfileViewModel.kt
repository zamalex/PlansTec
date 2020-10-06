package creativitysol.com.planstech.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import creativitysol.com.planstech.api.Retrofit
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class ProfileViewModel : ViewModel(){


    var updateResponse = MutableLiveData<ResponseBody>()


        fun updateProfile(token: String,
                          file: MultipartBody.Part?,
                          partMap: Map<String, RequestBody>){
            Retrofit.Api.updateProfile(token,file,partMap).enqueue(object : retrofit2.Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful){
                        updateResponse.value = response.body()
                    }else
                        updateResponse.value = null
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    updateResponse.value = null
                }
            })
        }

}