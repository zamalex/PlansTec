package creativitysol.com.planstech.packages

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import creativitysol.com.planstech.api.Retrofit
import creativitysol.com.planstech.api.SuccessModel

import creativitysol.com.planstech.packages.model.PlanModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.PartMap

class PlanViewModel : ViewModel() {

    var plans: MutableLiveData<PlanModel> = MutableLiveData()
    var myplans: MutableLiveData<PlanModel> = MutableLiveData()
    var subscribeResponse: MutableLiveData<SuccessModel> = MutableLiveData()

    fun getTrainings(token: String) {
        Retrofit.Api.getPlans(token).enqueue(object : Callback<PlanModel> {
            override fun onFailure(call: Call<PlanModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<PlanModel>, response: Response<PlanModel>) {
                if (response.isSuccessful)
                    plans.value = response.body()
            }
        })
    }

    fun getMyPlans(token: String) {
        Retrofit.Api.getMyPlans(token).enqueue(object : Callback<PlanModel> {
            override fun onFailure(call: Call<PlanModel>, t: Throwable) {
                myplans.value = null

            }

            override fun onResponse(call: Call<PlanModel>, response: Response<PlanModel>) {
                if (response.isSuccessful)
                    myplans.value = response.body()
                else myplans.value = null

            }
        })
    }


    fun subscribeToPackage(
        token: String,
        file: MultipartBody.Part?,
        partMap: Map<String, RequestBody>
    ) {
        Retrofit.Api.subscribeToPackage(token, file, partMap)
            .enqueue(object : Callback<SuccessModel> {
                override fun onFailure(call: Call<SuccessModel>, t: Throwable) {
                    subscribeResponse.value = null
                }

                override fun onResponse(
                    call: Call<SuccessModel>,
                    response: Response<SuccessModel>
                ) {
                    if (response.isSuccessful)
                        subscribeResponse.value = response.body()
                    else
                        subscribeResponse.value = null

                }
            })
    }

    fun subscribeToCourse(
        token: String,
        file: MultipartBody.Part?,
        partMap: Map<String, RequestBody>
    ) {
        Retrofit.Api.subscribeToCourse(token, file, partMap)
            .enqueue(object : Callback<SuccessModel> {
                override fun onFailure(call: Call<SuccessModel>, t: Throwable) {
                    subscribeResponse.value = null
                }

                override fun onResponse(
                    call: Call<SuccessModel>,
                    response: Response<SuccessModel>
                ) {
                    if (response.isSuccessful)
                        subscribeResponse.value = response.body()
                    else
                        subscribeResponse.value = null

                }
            })
    }


}