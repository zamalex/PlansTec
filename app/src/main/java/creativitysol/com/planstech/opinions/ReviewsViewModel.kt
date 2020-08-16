package creativitysol.com.planstech.opinions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import creativitysol.com.planstech.api.Retrofit
import creativitysol.com.planstech.home.model.ReviewsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewsViewModel:ViewModel(){


    var reviews: MutableLiveData<ReviewsModel> = MutableLiveData()

    fun getReviews(){

        Retrofit.Api.getAllReviews().enqueue(object : Callback<ReviewsModel> {
            override fun onFailure(call: Call<ReviewsModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ReviewsModel>, response: Response<ReviewsModel>) {
                reviews.value = response.body()
            }
        })
    }
}