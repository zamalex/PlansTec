package creativitysol.com.planstech.notifications.domain

import androidx.lifecycle.MutableLiveData
import creativitysol.com.planstech.base.Executors
import creativitysol.com.planstech.base.UseCase
import creativitysol.com.planstech.notifications.data.model.NotificationData
import creativitysol.com.planstech.notifications.data.repository.NotificationsRepo
import io.reactivex.rxjava3.disposables.CompositeDisposable

class NotificationsUseCase(
    private val compositeDisposable: CompositeDisposable,
    private val executors: Executors,
    private val notificationsRepo: NotificationsRepo
) : UseCase<Any?, Unit> {

    override fun execute(value: Any?) {

        compositeDisposable.add(
            notificationsRepo.getNotifications()
                .subscribeOn(executors.getIOThread())
                .subscribe({
                    getNotifications.postValue(it)
                }, {
                    getError.postValue(it)
                })

        )
    }

    override fun flushResources() {
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }

    val getNotifications = MutableLiveData<NotificationData>()
    val getError = MutableLiveData<Throwable>()

}
