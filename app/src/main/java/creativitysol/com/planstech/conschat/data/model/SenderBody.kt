package creativitysol.com.planstech.conschat.data.model

import com.google.gson.annotations.SerializedName

data class SenderBody(
    @SerializedName("receiver_id")
    val receiverId: Int,
    val message: String
)
