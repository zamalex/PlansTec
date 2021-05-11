package creativitysol.com.planstech.notifications.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import creativitysol.com.planstech.base.Executors
import creativitysol.com.planstech.notifications.data.model.NotificationResponse
import creativitysol.com.planstech.notifications.data.repository.NotificationsRepo
import creativitysol.com.planstech.notifications.domain.NotificationsUseCase

class NotificationsViewModel
    (private val notificationsUseCase: NotificationsUseCase, private val executors: Executors, private val notificationsRepo: NotificationsRepo
) : ViewModel() {


    fun getNotification(page:Int){
        notificationsRepo.getNotifications(page)
            .subscribeOn(executors.getIOThread())
            .subscribe({
                notifications.postValue(it)
            }, {
                Log.e("error",it.localizedMessage)
                error.postValue(it)
            })
    }

    var notifications =  MutableLiveData<NotificationResponse>()

    val error = MutableLiveData<Throwable>()
}
