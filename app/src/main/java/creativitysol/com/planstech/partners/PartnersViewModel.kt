package creativitysol.com.planstech.partners

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import creativitysol.com.planstech.api.Retrofit
import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.partners.model.PartnerModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PartnersViewModel:ViewModel() {

    var result = MutableLiveData<PartnerModel>()
    fun getPartners(){

        Retrofit.Api.getPartners().enqueue(object : Callback<PartnerModel> {
            override fun onFailure(call: Call<PartnerModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<PartnerModel>, response: Response<PartnerModel>) {
                if (response.isSuccessful)
                    result.value = response.body()
            }
        })
    }
}