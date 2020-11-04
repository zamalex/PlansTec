package creativitysol.com.planstech.favorites.data.repository

import creativitysol.com.planstech.favorites.data.datasource.network.ListFavouritesAPI
import creativitysol.com.planstech.favorites.data.model.TrainingResult
import creativitysol.com.planstech.login.model.LoginModel
import io.paperdb.Paper
import io.reactivex.rxjava3.core.Single

class ListFavouriteRepoImpl (private val listFavouritesAPI: ListFavouritesAPI)
    : ListFavouriteRepo {
    override fun getAllFavourite(type : String) : Single<TrainingResult> {
        return listFavouritesAPI.getAllFavourites(type,"Bearer ${(Paper.book().read("login",
            LoginModel()
        )as LoginModel).data.token}")
    }
}