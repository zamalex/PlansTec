package creativitysol.com.planstech.coursedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import creativitysol.com.planstech.api.Retrofit
import creativitysol.com.planstech.api.SuccessModel
import creativitysol.com.planstech.coursedetails.model.SingleCourse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrainingViewModel :ViewModel(){

    var course: MutableLiveData<SingleCourse> = MutableLiveData()

    fun getcourse(id:String){

        Retrofit.Api.getTraining(id).enqueue(object : Callback<SingleCourse> {
            override fun onFailure(call: Call<SingleCourse>, t: Throwable) {

            }

            override fun onResponse(call: Call<SingleCourse>, response: Response<SingleCourse>) {
                course.value = response.body()
            }
        })
    }





}