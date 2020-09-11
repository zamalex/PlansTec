package creativitysol.com.planstech.favorites.data.repository

import creativitysol.com.planstech.favorites.data.datasource.network.AddToFavouriteAPI
import creativitysol.com.planstech.favorites.data.model.TrainingBody
import creativitysol.com.planstech.favorites.data.model.TrainingResult
import io.reactivex.rxjava3.core.Single

class AddToFavouriteRepoImpl(private val addToFavouriteAPI: AddToFavouriteAPI) :
    AddToFavouriteRepo {
    override fun addToFavourite(trainingBody: TrainingBody): Single<TrainingResult> {
        return addToFavouriteAPI.addToFavourite(trainingBody = trainingBody)
    }
}