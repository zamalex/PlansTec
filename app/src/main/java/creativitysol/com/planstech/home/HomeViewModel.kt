package creativitysol.com.planstech.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import creativitysol.com.planstech.api.Retrofit
import creativitysol.com.planstech.home.model.ArticlesModel
import creativitysol.com.planstech.home.model.ReviewsModel
import creativitysol.com.planstech.home.model.StatModel
import creativitysol.com.planstech.home.model.TrainingModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel(){


    var trainings:MutableLiveData<TrainingModel> = MutableLiveData()
    var articles:MutableLiveData<ArticlesModel> = MutableLiveData()
    var reviews:MutableLiveData<ReviewsModel> = MutableLiveData()
    var stats:MutableLiveData<StatModel> = MutableLiveData()

    fun getTrainings(){
        Retrofit.Api.getHomeTrainings().enqueue(object : Callback<TrainingModel>{
            override fun onFailure(call: Call<TrainingModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<TrainingModel>, response: Response<TrainingModel>) {
                if (response.isSuccessful)
                trainings.value = response.body()
            }
        })
    }


    fun getArticles(){
        Retrofit.Api.getHomeArticles().enqueue(object : Callback<ArticlesModel>{
            override fun onFailure(call: Call<ArticlesModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ArticlesModel>, response: Response<ArticlesModel>) {
                if (response.isSuccessful)
                articles.value = response.body()
            }
        })
    }

    fun getReviews(){
        Retrofit.Api.getHomeReviews().enqueue(object : Callback<ReviewsModel>{
            override fun onFailure(call: Call<ReviewsModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ReviewsModel>, response: Response<ReviewsModel>) {
                if (response.isSuccessful)
                reviews.value = response.body()
            }
        })
    }


    fun getStats(){
        Retrofit.Api.getStats().enqueue(object : Callback<StatModel>{
            override fun onFailure(call: Call<StatModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<StatModel>, response: Response<StatModel>) {

                stats.value = response.body()

            }
        })
    }

}