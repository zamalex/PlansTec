package creativitysol.com.planstech.notifications.data.model

import com.google.gson.annotations.SerializedName
import java.util.stream.IntStream

data class Notifications(
    val id: Int,

    val title: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

data class NotificationData(
    val data: List<Notifications>
)

