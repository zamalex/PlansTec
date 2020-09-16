package creativitysol.com.planstech.conschat.data.datasource.network

import creativitysol.com.planstech.conschat.data.model.ChatHistory
import creativitysol.com.planstech.conschat.data.model.HistoryData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ChatHistoryAPI {
    @GET("auth/chats")
    fun getChatHistory(): Single<HistoryData>
}