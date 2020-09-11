package creativitysol.com.planstech.favorites.data.repository

import creativitysol.com.planstech.favorites.data.model.TrainingResult
import io.reactivex.rxjava3.core.Single

interface ListFavouriteRepo {
    fun getAllFavourite(type: String): Single<TrainingResult>
}