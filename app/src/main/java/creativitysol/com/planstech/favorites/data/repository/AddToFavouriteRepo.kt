package creativitysol.com.planstech.favorites.data.repository

import creativitysol.com.planstech.favorites.data.model.AddToFav
import creativitysol.com.planstech.favorites.data.model.TrainingBody
import io.reactivex.rxjava3.core.Single

interface AddToFavouriteRepo {
    fun addToFavourite(trainingBody: TrainingBody): Single<AddToFav>
}