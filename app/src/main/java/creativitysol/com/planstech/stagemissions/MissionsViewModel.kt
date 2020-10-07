package creativitysol.com.planstech.stagemissions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import creativitysol.com.planstech.api.Retrofit
import creativitysol.com.planstech.stagemissions.model.MissionsModel
import creativitysol.com.planstech.stagequestions.model.QuestionsModel
import retrofit2.Call
import retrofit2.Response

class MissionsViewModel : ViewModel() {

    var questionsResopnse = MutableLiveData<MissionsModel>()


    fun getStagesOfPackage(
        token: String,
        id: String
    ) {
        Retrofit.Api.getMissions(token, id)
            .enqueue(object : retrofit2.Callback<MissionsModel> {
                override fun onResponse(
                    call: Call<MissionsModel>,
                    response: Response<MissionsModel>
                ) {
                    if (response.isSuccessful) {
                        questionsResopnse.value = response.body()
                    } else
                        questionsResopnse.value = null
                }

                override fun onFailure(call: Call<MissionsModel>, t: Throwable) {
                    questionsResopnse.value = null
                }
            })
    }
}