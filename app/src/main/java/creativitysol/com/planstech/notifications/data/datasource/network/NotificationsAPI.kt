package creativitysol.com.planstech.notifications.data.datasource.network

import creativitysol.com.planstech.notifications.data.model.NotificationData
import creativitysol.com.planstech.notifications.data.model.NotificationResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NotificationsAPI {
    @GET("notifications")
    fun getNotifications(@Header("Authorization") token:String,@Query("page")page:Int) : Single<NotificationResponse>
}
