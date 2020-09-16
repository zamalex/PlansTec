package creativitysol.com.planstech.conschat.presentation

import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import creativitysol.com.planstech.conschat.data.model.SenderBody
import creativitysol.com.planstech.conschat.domain.ChatHistoryUseCase
import creativitysol.com.planstech.conschat.domain.SendChatUseCase

class ConsultationChatViewModel(
    private val chatHistoryUseCase: ChatHistoryUseCase,
    private val sendChatUseCase: SendChatUseCase
) : ViewModel() {

    fun requestChatHistory(){
        chatHistoryUseCase.execute(null)
    }

    fun sendChatMessage(jsonObject: SenderBody) {
        sendChatUseCase.execute(jsonObject)
    }

    val getChatHistory = chatHistoryUseCase.chatHistoryList
    val errorChatHistory = chatHistoryUseCase.historyError

    val getStatusSending = sendChatUseCase.sendMessageStatus
    val errorSending = sendChatUseCase.sendError

}