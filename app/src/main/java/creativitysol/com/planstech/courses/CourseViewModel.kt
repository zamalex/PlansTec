package creativitysol.com.planstech.courses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import creativitysol.com.planstech.api.Retrofit
import creativitysol.com.planstech.home.model.TrainingModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseViewModel:ViewModel() {

    var trainings: MutableLiveData<TrainingModel> = MutableLiveData()

    fun getArticles(){

        Retrofit.Api.getAllTrainings().enqueue(object : Callback<TrainingModel> {
            override fun onFailure(call: Call<TrainingModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<TrainingModel>, response: Response<TrainingModel>) {
                trainings.value = response.body()
            }
        })
    }
}