package creativitysol.com.planstech.favorites.presentation.viewmodel

import androidx.lifecycle.ViewModel
import creativitysol.com.planstech.favorites.data.model.TrainingBody
import creativitysol.com.planstech.favorites.domain.AddToFavouritesUseCase

class AddToFavouritesViewModel(private val addToFavouritesUseCase: AddToFavouritesUseCase) :
    ViewModel() {

    fun addToFavourites(trainingBody: TrainingBody) {
        addToFavouritesUseCase.execute(trainingBody)
    }

    val trainingResults = addToFavouritesUseCase.result
    val error = addToFavouritesUseCase.error
}
