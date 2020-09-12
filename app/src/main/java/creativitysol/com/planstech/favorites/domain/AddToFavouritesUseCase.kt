package creativitysol.com.planstech.favorites.domain

import androidx.lifecycle.MutableLiveData
import creativitysol.com.planstech.base.Executors
import creativitysol.com.planstech.base.UseCase
import creativitysol.com.planstech.favorites.data.model.AddToFav
import creativitysol.com.planstech.favorites.data.model.TrainingBody
import creativitysol.com.planstech.favorites.data.repository.AddToFavouriteRepo
import io.reactivex.rxjava3.disposables.CompositeDisposable

class AddToFavouritesUseCase(
    private val compositeDisposable: CompositeDisposable,
    private val executors: Executors,
    private val addToFavouriteRepo: AddToFavouriteRepo
) : UseCase<TrainingBody, Unit> {

    override fun execute(value: TrainingBody) {
        compositeDisposable.add(
            addToFavouriteRepo.addToFavourite(trainingBody = value)
                .subscribeOn(executors.getIOThread())
                .subscribe({
                    if (it != null)
                        result.postValue(it)
                }, {
                    error.postValue(it)
                })
        )
    }

    override fun flushResources() {
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }

    val result = MutableLiveData<AddToFav>()
    val error = MutableLiveData<Throwable>()
}