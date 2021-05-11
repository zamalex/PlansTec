package creativitysol.com.planstech.notifications.domain

import android.util.Log
import androidx.lifecycle.MutableLiveData
import creativitysol.com.planstech.base.Executors
import creativitysol.com.planstech.base.UseCase
import creativitysol.com.planstech.notifications.data.model.NotificationData
import creativitysol.com.planstech.notifications.data.model.NotificationResponse
import creativitysol.com.planstech.notifications.data.repository.NotificationsRepo
import io.reactivex.rxjava3.disposables.CompositeDisposable

class NotificationsUseCase(
    private val compositeDisposable: CompositeDisposable,
    private val executors: Executors,
    private val notificationsRepo: NotificationsRepo
) : UseCase<Any?, Unit> {

    override fun execute(value: Any?) {

        compositeDisposable.add(
            notificationsRepo.getNotifications(1)
                .subscribeOn(executors.getIOThread())
                .subscribe({
                    getNotifications.postValue(it)
                }, {
                    Log.e("error",it.localizedMessage)
                    getError.postValue(it)
                })

        )
    }

    override fun flushResources() {
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }

    val getNotifications = MutableLiveData<NotificationResponse>()
    val getError = MutableLiveData<Throwable>()

}
