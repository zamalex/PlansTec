package creativitysol.com.planstech.favorites.domain

import androidx.lifecycle.MutableLiveData
import creativitysol.com.planstech.base.Executors
import creativitysol.com.planstech.base.UseCase
import creativitysol.com.planstech.favorites.data.model.TrainingBody
import creativitysol.com.planstech.favorites.data.model.TrainingResult
import creativitysol.com.planstech.favorites.data.repository.ListFavouriteRepo
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ListFavouritesUseCase(
    private val compositeDisposable: CompositeDisposable,
    private val executors: Executors,
    private val listFavouriteRepo: ListFavouriteRepo
) : UseCase<String, Unit> {

    override fun execute(type : String) {
        compositeDisposable.add(
            listFavouriteRepo.getAllFavourite(type)
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

    val result = MutableLiveData<TrainingResult>()
    val error = MutableLiveData<Throwable>()
}