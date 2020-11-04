package creativitysol.com.planstech.notifications.data.repository

import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.notifications.data.datasource.network.NotificationsAPI
import creativitysol.com.planstech.notifications.data.model.NotificationData
import io.paperdb.Paper
import io.reactivex.rxjava3.core.Single

class NotificationsRepoImpl(private val notificationsAPI: NotificationsAPI) : NotificationsRepo {
    override fun getNotifications(): Single<NotificationData> {
        return notificationsAPI.getNotifications("Bearer ${(Paper.book().read("login",LoginModel())as LoginModel).data.token}")
    }
}