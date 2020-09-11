package creativitysol.com.planstech.favorites.presentation.viewmodel

import androidx.lifecycle.ViewModel
import creativitysol.com.planstech.favorites.domain.ListFavouritesUseCase

class ListFavouritesViewModel(private val listFavouritesUseCase: ListFavouritesUseCase) :
    ViewModel() {

    fun getAllFavouritesByType(type: String) {
        listFavouritesUseCase.execute(type)
    }

    val trainingResults = listFavouritesUseCase.result
    val error = listFavouritesUseCase.error
}
