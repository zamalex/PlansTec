package creativitysol.com.planstech.articledetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import creativitysol.com.planstech.api.Retrofit
import creativitysol.com.planstech.articledetails.model.SingleArticle
import creativitysol.com.planstech.login.model.LoginModel
import io.paperdb.Paper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleViewModel :ViewModel(){

    var article: MutableLiveData<SingleArticle> = MutableLiveData()

    fun getArticle(id:String){

        Retrofit.Api.getArticle(id,"Bearer ${Paper.book().read("login",LoginModel()).data.token}").enqueue(object : Callback<SingleArticle> {
            override fun onFailure(call: Call<SingleArticle>, t: Throwable) {

            }

            override fun onResponse(call: Call<SingleArticle>, response: Response<SingleArticle>) {
                article.value = response.body()
            }
        })
    }
}