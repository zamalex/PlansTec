package creativitysol.com.planstech.follow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import creativitysol.com.planstech.api.Retrofit
import retrofit2.Call
import retrofit2.Response

class FollowViewModel:ViewModel() {

    fun getFollow():MutableLiveData<FollowReponse>{
        var followReponse  = MutableLiveData<FollowReponse>()

        Retrofit.Api.getFollow().enqueue(object :retrofit2.Callback<FollowReponse>{
            override fun onFailure(call: Call<FollowReponse>, t: Throwable) {
                followReponse.value=null

            }

            override fun onResponse(call: Call<FollowReponse>, response: Response<FollowReponse>) {
                if (response.isSuccessful){
                    followReponse.value=response.body()
                }else{
                    followReponse.value= null
                }
            }
        })
        return followReponse
    }
}