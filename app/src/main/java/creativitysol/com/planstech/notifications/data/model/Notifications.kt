package creativitysol.com.planstech.notifications.data.model

import com.google.gson.annotations.SerializedName
import java.util.stream.IntStream

data class Notifications(
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    val title: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)

data class NotificationData(
    val data: List<Notifications>
)
