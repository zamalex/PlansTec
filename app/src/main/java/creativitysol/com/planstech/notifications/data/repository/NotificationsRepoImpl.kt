package creativitysol.com.planstech.notifications.data.repository

import creativitysol.com.planstech.notifications.data.datasource.network.NotificationsAPI
import creativitysol.com.planstech.notifications.data.model.NotificationData
import io.reactivex.rxjava3.core.Single

class NotificationsRepoImpl(private val notificationsAPI: NotificationsAPI) : NotificationsRepo {
    override fun getNotifications(): Single<NotificationData> {
        return notificationsAPI.getNotifications()
    }
}