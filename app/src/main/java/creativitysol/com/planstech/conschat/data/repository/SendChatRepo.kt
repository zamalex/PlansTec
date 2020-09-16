package creativitysol.com.planstech.conschat.data.repository

import creativitysol.com.planstech.conschat.data.model.SenderBody
import io.reactivex.rxjava3.core.Completable

interface SendChatRepo {
    fun sendChatMessage(jsonObject: SenderBody): Completable
}