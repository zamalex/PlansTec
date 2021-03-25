package creativitysol.com.planstech.home.model


import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("data")
    var `data`: String = "",
    @SerializedName("message")
    var message: String = "",
    @SerializedName("status_code")
    var statusCode: Int = 0
)