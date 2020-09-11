package creativitysol.com.planstech.notifications.presentation

import androidx.lifecycle.ViewModel
import creativitysol.com.planstech.notifications.domain.NotificationsUseCase

class NotificationsViewModel
    (private val notificationsUseCase: NotificationsUseCase) : ViewModel() {

    init {
        notificationsUseCase.execute(null)
    }

    val notifications = notificationsUseCase.getNotifications

    val error = notificationsUseCase.getError
}
