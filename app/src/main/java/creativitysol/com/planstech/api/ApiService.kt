package creativitysol.com.planstech.api

import com.google.gson.JsonObject
import creativitysol.com.planstech.about.DataResponse
import creativitysol.com.planstech.articledetails.model.SingleArticle
import creativitysol.com.planstech.coursedetails.model.SingleCourse
import creativitysol.com.planstech.follow.FollowReponse
import creativitysol.com.planstech.gladtoserve.model.ContactData
import creativitysol.com.planstech.gladtoserve.model.Services
import creativitysol.com.planstech.home.model.ArticlesModel
import creativitysol.com.planstech.home.model.ReviewsModel
import creativitysol.com.planstech.home.model.StatModel
import creativitysol.com.planstech.home.model.TrainingModel
import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.packages.model.PlanModel
import creativitysol.com.planstech.partners.model.PartnerModel
import creativitysol.com.planstech.password.presentation.VerifyResponse
import creativitysol.com.planstech.profile.ProfileResponse
import creativitysol.com.planstech.register.model.RegisterModel
import creativitysol.com.planstech.stagemissions.model.MissionsModel
import creativitysol.com.planstech.stagequestions.model.QuestionsModel
import creativitysol.com.planstech.stages.model.StagesModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @GET("packages")
    fun getPlans(@Header("Authorization") bearerToken: String): Call<PlanModel>

    @GET("packages/user-subscriptions")
    fun getMyPlans(@Header("Authorization") bearerToken: String): Call<PlanModel>


    @GET("pages/contact-us")
    fun getContactDate(): Call<ContactData>


    @GET("pages/about-us")
    fun aboutUs(): Call<DataResponse>

    @GET("pages/terms")
    fun terms(): Call<DataResponse>


    @GET("auth/article_desc/{id}")
    fun getArticle(@Path("id") id: String): Call<SingleArticle>

    @GET("auth/partners")
    fun getPartners(): Call<PartnerModel>

    @GET("statistics")
    fun getStats(): Call<StatModel>

    @GET("auth/training_desc/{id}")
    fun getTraining(@Path("id") id: String): Call<SingleCourse>


    @GET("auth/verify/{code}")
    fun verifyCode(@Path("code") code: String): Call<VerifyResponse>

    @GET("auth/services_types")
    fun getServices(@Header("Authorization") bearerToken: String): Call<Services>

    @GET("auth/profile")
    fun getProfile(@Header("Authorization") bearerToken: String): Call<RegisterModel>


    @GET("packages/{id}/stages")
    fun getStagesOfPackage(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: String
    ): Call<StagesModel>

    @GET("stages/{id}/questions")
    fun getQuestions(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: String
    ): Call<QuestionsModel>


    @GET("stages/{id}/tasks")
    fun getMissions(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: String
    ): Call<MissionsModel>


    @GET("auth/my_favorites_trainings/{type}")
    fun getFavs(
        @Header("Authorization") bearerToken: String,
        @Path("type") type: String
    ): Call<ResponseBody>

    @GET("pages/contact-us")
    fun getFollow(): Call<FollowReponse>


    @POST("auth/add_to_my_favorites_trainings")
    fun addFav(
        @Header("Authorization") bearerToken: String,
        @Body body: JsonObject
    ): Call<ResponseBody>


    @POST("auth/register")
    fun register(@Body body: JsonObject): Call<RegisterModel>

    @POST("auth/login")
    fun login(@Body body: JsonObject): Call<LoginModel>

    @Multipart
    @POST("packages/subscribe")
    fun subscribeToPackage(
        @Header("Authorization") bearerToken: String,
        @Part file: MultipartBody.Part?,
        @PartMap() partMap: Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<SuccessModel>


    @Multipart
    @POST("pay/subscribe-to-courses")
    fun subscribeToCourse(
        @Header("Authorization") bearerToken: String,
        @Part file: MultipartBody.Part?,
        @PartMap() partMap: Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<SuccessModel>


    @Multipart
    @POST("auth/update_profile")
    fun updateProfile(
        @Header("Authorization") bearerToken: String,
        @Part file: MultipartBody.Part?,
        @PartMap() partMap: Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<ProfileResponse>

    @POST("auth/leave_messages")
    fun leaveMessage(
        @Body body: JsonObject,
        @Header("Authorization") bearerToken: String
    ): Call<ResponseBody>


    @Multipart
    @POST("stages/{id}/tasks/set-answers")
    fun sendMissions(
        @Header("Authorization") bearerToken: String,
        @Part files: List<MultipartBody.Part?>?,
        @Path("id") id: String,
        @PartMap partMap: HashMap<String, RequestBody>
    ): Call<ResponseBody>


    @Multipart
    @POST("stages/{id}/questions/set-answers")
    fun sendAnswers(
        @Header("Authorization") bearerToken: String,
        @PartMap partMap: HashMap<String, RequestBody>,
        @Path("id") id: String,

        ): Call<ResponseBody>


    @POST("notifications/subscribe")
    fun snedNotToken(
        @Header("Authorization") bearerToken: String,
        @Body body: JsonObject

        ): Call<ResponseBody>


    @POST("auth/logout")
    fun logout(
        @Header("Authorization") bearerToken: String
    ): Call<ResponseBody>
}