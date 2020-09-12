package creativitysol.com.planstech.favorites.data.repository

import creativitysol.com.planstech.favorites.data.datasource.network.AddToFavouriteAPI
import creativitysol.com.planstech.favorites.data.model.AddToFav
import creativitysol.com.planstech.favorites.data.model.TrainingBody
import io.reactivex.rxjava3.core.Single

class AddToFavouriteRepoImpl(private val addToFavouriteAPI: AddToFavouriteAPI) :
    AddToFavouriteRepo {
    override fun addToFavourite(trainingBody: TrainingBody): Single<AddToFav> {
        return addToFavouriteAPI.addToFavourite(trainingBody = trainingBody)
    }
}