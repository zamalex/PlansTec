package creativitysol.com.planstech.stagequestions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import creativitysol.com.planstech.api.Retrofit
import creativitysol.com.planstech.stagequestions.model.QuestionsModel
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class QuestionsViewModel : ViewModel() {

    var questionsResopnse = MutableLiveData<QuestionsModel>()
    var submit = MutableLiveData<ResponseBody>()


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


    fun submit(
        token: String,
        id: String,
        partMap: HashMap<String, RequestBody>
    ) {
        Retrofit.Api.sendAnswers(token,partMap, id)
            .enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        submit.value = response.body()
                    } else
                        submit.value = null
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    submit.value = null
                }
            })
    }
}