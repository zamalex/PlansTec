package creativitysol.com.planstech.api


import com.google.gson.annotations.SerializedName

data class SuccessModel(
    @SerializedName("status_code")
    var statusCode: Int = 0,
    var success: Boolean = false
)