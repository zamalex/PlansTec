package creativitysol.com.planstech.conschat.data.repository

import com.google.gson.JsonObject
import creativitysol.com.planstech.conschat.data.datasource.network.SendChatAPI
import creativitysol.com.planstech.conschat.data.model.SenderBody
import creativitysol.com.planstech.login.model.LoginModel
import io.paperdb.Paper
import io.reactivex.rxjava3.core.Completable

class SendChatRepoImpl(
    private val sendChatAPI: SendChatAPI
) : SendChatRepo {
    override fun sendChatMessage(jsonObject: SenderBody): Completable {
        return sendChatAPI.sendChatMessage(jsonObject,"Bearer ${(Paper.book().read("login",
            LoginModel()
        )as LoginModel).data.token}")
    }
}