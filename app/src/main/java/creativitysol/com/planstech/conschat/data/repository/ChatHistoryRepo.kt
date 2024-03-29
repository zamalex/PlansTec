package creativitysol.com.planstech.conschat.data.repository

import creativitysol.com.planstech.conschat.data.model.ChatMessages
import io.reactivex.rxjava3.core.Single

interface ChatHistoryRepo {
    fun getChatHistory(): Single<ChatMessages>
}