package creativitysol.com.planstech.conschat.data.datasource.network

import creativitysol.com.planstech.conschat.data.model.SenderBody
import io.reactivex.rxjava3.core.Completable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SendChatAPI {
    @POST("messages/send")
    fun sendChatMessage(
        @Body senderBody: SenderBody,
        @Header("Authorization")token:String
    ): Completable
}