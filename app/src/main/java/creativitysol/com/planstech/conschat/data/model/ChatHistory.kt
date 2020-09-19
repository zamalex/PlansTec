package creativitysol.com.planstech.conschat.data.model

import com.google.gson.annotations.SerializedName

data class ChatHistory(
    @SerializedName("sender_id")
    val senderId: Int?,
    @SerializedName("receiver_id")
    val receiverId: Int?,
    val archived: String,
    val messages: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

data class HistoryData(
    val data: List<ChatHistory>
)
