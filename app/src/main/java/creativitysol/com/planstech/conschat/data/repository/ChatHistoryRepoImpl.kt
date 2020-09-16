package creativitysol.com.planstech.conschat.data.repository

import creativitysol.com.planstech.conschat.data.datasource.network.ChatHistoryAPI
import creativitysol.com.planstech.conschat.data.model.HistoryData
import io.reactivex.rxjava3.core.Single

class ChatHistoryRepoImpl
    (private val chatHistoryAPI: ChatHistoryAPI): ChatHistoryRepo {
    override fun getChatHistory(): Single<HistoryData> {
        return chatHistoryAPI.getChatHistory()
    }
}