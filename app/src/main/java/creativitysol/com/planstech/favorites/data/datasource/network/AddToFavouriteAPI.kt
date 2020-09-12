package creativitysol.com.planstech.favorites.data.datasource.network

import creativitysol.com.planstech.favorites.data.model.AddToFav
import creativitysol.com.planstech.favorites.data.model.TrainingBody
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AddToFavouriteAPI {
    @POST("auth/add_to_my_favorites_trainings")
    fun addToFavourite(@Body trainingBody: TrainingBody): Single<AddToFav>
}
