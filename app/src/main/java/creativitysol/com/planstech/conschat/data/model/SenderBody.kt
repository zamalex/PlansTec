package creativitysol.com.planstech.conschat.data.model

import com.google.gson.annotations.SerializedName

data class SenderBody(
    @SerializedName("conversation_id")
    val conversation_id: String,
    val message: String
)
