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


    var updateResponse = MutableLiveData<ProfileResponse>()


        fun updateProfile(token: String,
                          file: MultipartBody.Part?,
                          partMap: Map<String, RequestBody>){
            Retrofit.Api.updateProfile(token,file,partMap).enqueue(object : retrofit2.Callback<ProfileResponse>{
                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    if (response.isSuccessful){
                        updateResponse.value = response.body()
                    }else
                        updateResponse.value = null
                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    updateResponse.value = null
                }
            })
        }

}