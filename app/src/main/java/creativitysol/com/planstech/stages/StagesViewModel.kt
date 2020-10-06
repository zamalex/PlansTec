package creativitysol.com.planstech.stages

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import creativitysol.com.planstech.api.Retrofit
import creativitysol.com.planstech.stages.model.StagesModel

import retrofit2.Call
import retrofit2.Response

class StagesViewModel : ViewModel() {
    var stagesResponse = MutableLiveData<StagesModel>()


    fun getStagesOfPackage(
        token: String,
        id: String
    ) {
        Retrofit.Api.getStagesOfPackage(token, id)
            .enqueue(object : retrofit2.Callback<StagesModel> {
                override fun onResponse(
                    call: Call<StagesModel>,
                    response: Response<StagesModel>
                ) {
                    if (response.isSuccessful) {
                        stagesResponse.value = response.body()
                    } else
                        stagesResponse.value = null
                }

                override fun onFailure(call: Call<StagesModel>, t: Throwable) {
                    stagesResponse.value = null
                }
            })
    }
}