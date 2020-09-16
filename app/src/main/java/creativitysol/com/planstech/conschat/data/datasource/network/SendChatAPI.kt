package creativitysol.com.planstech.conschat.data.datasource.network

import creativitysol.com.planstech.conschat.data.model.SenderBody
import io.reactivex.rxjava3.core.Completable
import retrofit2.http.Body
import retrofit2.http.POST

interface SendChatAPI {
    @POST("auth/send_message")
    fun sendChatMessage(
        @Body senderBody: SenderBody
    ): Completable
}