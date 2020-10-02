package creativitysol.com.planstech.password.presentation


import com.google.gson.annotations.SerializedName

data class VerifyResponse(
    var message: String = "",
    @SerializedName("status_code")
    var statusCode: Int = 0
)