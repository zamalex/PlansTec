package creativitysol.com.planstech.packages

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import creativitysol.com.planstech.api.Retrofit

import creativitysol.com.planstech.packages.model.PlanModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlanViewModel: ViewModel() {

    var plans: MutableLiveData<PlanModel> = MutableLiveData()


    fun getTrainings(token:String){
        Retrofit.Api.getPlans(token).enqueue(object : Callback<PlanModel> {
            override fun onFailure(call: Call<PlanModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<PlanModel>, response: Response<PlanModel>) {
                if (response.isSuccessful)
                    plans.value = response.body()
            }
        })
    }


}