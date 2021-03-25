package creativitysol.com.planstech.articles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import creativitysol.com.planstech.api.Retrofit
import creativitysol.com.planstech.home.model.ArticlesModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticlesViewModel:ViewModel() {
    var articles: MutableLiveData<ArticlesModel> = MutableLiveData()

    fun getArticles(page:Int){

        Retrofit.Api.getAllArticles(page).enqueue(object : Callback<ArticlesModel>{
            override fun onFailure(call: Call<ArticlesModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ArticlesModel>, response: Response<ArticlesModel>) {
                articles.value = response.body()
            }
        })
    }
}