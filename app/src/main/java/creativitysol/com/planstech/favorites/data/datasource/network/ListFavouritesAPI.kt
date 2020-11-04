package creativitysol.com.planstech.favorites.data.datasource.network

import creativitysol.com.planstech.favorites.data.model.TrainingResult
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ListFavouritesAPI {
    @GET("auth/my_favorites_trainings/{type}")
    fun getAllFavourites(@Path("type") type : String,@Header("Authorization")token:String) : Single<TrainingResult>
}