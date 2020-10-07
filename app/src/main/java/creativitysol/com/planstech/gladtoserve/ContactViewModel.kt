package creativitysol.com.planstech.gladtoserve

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import creativitysol.com.planstech.api.Retrofit
import creativitysol.com.planstech.gladtoserve.model.ContactData
import creativitysol.com.planstech.gladtoserve.model.Services
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactViewModel : ViewModel() {

    var res = MutableLiveData<ResponseBody>()
    var services = MutableLiveData<Services>()
    var contactData = MutableLiveData<ContactData>()
    fun send(jsonObject: JsonObject, token: String) {

        Retrofit.Api.leaveMessage(jsonObject, token).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                res.value = null

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful)
                    res.value = response.body()
                else
                    res.value = null

            }
        })
    }


    fun getServices(token: String) {

        Retrofit.Api.getServices(token).enqueue(object : Callback<Services> {
            override fun onFailure(call: Call<Services>, t: Throwable) {

            }

            override fun onResponse(call: Call<Services>, response: Response<Services>) {
                services.value = response.body()
            }
        })
    }


    fun getContactData() {

        Retrofit.Api.getContactDate().enqueue(object : Callback<ContactData> {
            override fun onFailure(call: Call<ContactData>, t: Throwable) {
                contactData.value=null
            }

            override fun onResponse(call: Call<ContactData>, response: Response<ContactData>) {
                if (response.isSuccessful) contactData.value = response.body()
                else contactData.value=null
            }
        })
    }
}