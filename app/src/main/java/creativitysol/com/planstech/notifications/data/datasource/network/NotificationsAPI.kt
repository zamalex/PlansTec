package creativitysol.com.planstech.notifications.data.datasource.network

import creativitysol.com.planstech.notifications.data.model.NotificationData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface NotificationsAPI {
    @GET("auth/notifications")
    fun getNotifications() : Single<NotificationData>
}
