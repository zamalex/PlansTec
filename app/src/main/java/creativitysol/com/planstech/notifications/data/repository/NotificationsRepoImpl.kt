package creativitysol.com.planstech.notifications.data.repository

import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.notifications.data.datasource.network.NotificationsAPI
import creativitysol.com.planstech.notifications.data.model.NotificationData
import creativitysol.com.planstech.notifications.data.model.NotificationResponse
import io.paperdb.Paper
import io.reactivex.rxjava3.core.Single

class NotificationsRepoImpl(private val notificationsAPI: NotificationsAPI) : NotificationsRepo {
    override fun getNotifications(page:Int): Single<NotificationResponse> {
        return notificationsAPI.getNotifications("Bearer ${(Paper.book().read("login",LoginModel())as LoginModel).data.token}",page)
    }
}