package creativitysol.com.planstech.stagequestions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import creativitysol.com.planstech.api.Retrofit
import creativitysol.com.planstech.stagequestions.model.QuestionsModel
import retrofit2.Call
import retrofit2.Response

class QuestionsViewModel : ViewModel() {

    var questionsResopnse = MutableLiveData<QuestionsModel>()


    fun getStagesOfPackage(
        token: String,
        id: String
    ) {
        Retrofit.Api.getQuestions(token, id)
            .enqueue(object : retrofit2.Callback<QuestionsModel> {
                override fun onResponse(
                    call: Call<QuestionsModel>,
                    response: Response<QuestionsModel>
                ) {
                    if (response.isSuccessful) {
                        questionsResopnse.value = response.body()
                    } else
                        questionsResopnse.value = null
                }

                override fun onFailure(call: Call<QuestionsModel>, t: Throwable) {
                    questionsResopnse.value = null
                }
            })
    }
}