package creativitysol.com.planstech.favorites.data.model

import com.google.gson.annotations.SerializedName

data class TrainingBody(
    val type: String,
    @SerializedName("training_id")
    val trainingId: Int
)

data class TrainingResult(
    val data: List<Data>?,
    @SerializedName("status_code")
    val statusCode: Int,
    val message: String
)

data class Data(
    val id: Int?,
    val type: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("training_id")
    val trainingId: Int,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    val title: String? = null,
    val image: String? = null,
    val description: String? = null,
    val status: String? = null,
    val address: String? = null,
    val duration: String? = null,
    @SerializedName("num_seats")
    val numSeats: String? = null,
    val price: String? = null
)