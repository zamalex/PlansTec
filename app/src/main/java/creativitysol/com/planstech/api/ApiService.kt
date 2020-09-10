package creativitysol.com.planstech.api

import com.google.gson.JsonObject
import creativitysol.com.planstech.articledetails.model.SingleArticle
import creativitysol.com.planstech.coursedetails.model.SingleCourse
import creativitysol.com.planstech.gladtoserve.model.Services
import creativitysol.com.planstech.home.model.ArticlesModel
import creativitysol.com.planstech.home.model.ReviewsModel
import creativitysol.com.planstech.home.model.StatModel
import creativitysol.com.planstech.home.model.TrainingModel
import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.packages.model.PlanModel
import creativitysol.com.planstech.partners.model.PartnerModel
import creativitysol.com.planstech.register.model.RegisterModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("auth/trainings_limit")
    fun getHomeTrainings(): Call<TrainingModel>

    @GET("auth/articles_limit")
    fun getHomeArticles(): Call<ArticlesModel>

    @GET("auth/reviews_limit")
    fun getHomeReviews(): Call<ReviewsModel>


    @GET("auth/articles")
    fun getAllArticles(): Call<ArticlesModel>

    @GET("auth/trainings")
    fun getAllTrainings(): Call<TrainingModel>

    @GET("auth/reviews")
    fun getAllReviews(): Call<ReviewsModel>

    @GET("auth/plans")
    fun getPlans(@Header("Authorization") bearerToken: String): Call<PlanModel>

    @GET("auth/chats")
    fun getChats(@Header("Authorization") bearerToken: String): Call<ResponseBody>

    @POST("auth/send_message")
    fun sendChat(
        @Header("Authorization") bearerToken: String,
        @Body jsonObject: JsonObject
    ): Call<ResponseBody>


    @GET("auth/article_desc/{id}")
    fun getArticle(@Path("id") id: String): Call<SingleArticle>

    @GET("auth/partners")
    fun getPartners(): Call<PartnerModel>

    @GET("auth/statistics")
    fun getStats(): Call<StatModel>

    @GET("auth/training_desc/{id}")
    fun getTraining(@Path("id") id: String): Call<SingleCourse>

    @GET("auth/services_types")
    fun getServices(@Header("Authorization") bearerToken: String): Call<Services>


    @GET("auth/my_favorites_trainings/{type}")
    fun getFavs(
        @Header("Authorization") bearerToken: String,
        @Path("type") type: String
    ): Call<ResponseBody>


    @POST("auth/add_to_my_favorites_trainings")
    fun addFav(
        @Header("Authorization") bearerToken: String,
        @Body body: JsonObject
    ): Call<ResponseBody>


    @POST("auth/register")
    fun register(@Body body: JsonObject): Call<RegisterModel>

    @POST("auth/login")
    fun login(@Body body: JsonObject): Call<LoginModel>

    @POST("auth/leave_messages")
    fun leaveMessage(
        @Body body: JsonObject,
        @Header("Authorization") bearerToken: String
    ): Call<ResponseBody>
}