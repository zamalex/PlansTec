package creativitysol.com.planstech.notifications.data.repository

import creativitysol.com.planstech.notifications.data.model.NotificationData
import io.reactivex.rxjava3.core.Single

interface NotificationsRepo {
    fun getNotifications() : Single<NotificationData>
}
