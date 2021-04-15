package creativitysol.com.planstech.conschat.data.datasource.network

import creativitysol.com.planstech.conschat.data.model.ChatMessages
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header

interface ChatHistoryAPI {
    @GET("messages/get-chat")
    fun getChatHistory(@Header("Authorization")token:String): Single<ChatMessages>
}