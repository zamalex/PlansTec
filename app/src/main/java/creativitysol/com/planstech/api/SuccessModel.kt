package creativitysol.com.planstech.api


import com.google.gson.annotations.SerializedName

data class SuccessModel(
    var `data`: Data = Data(),
    @SerializedName("status_code")
    var statusCode: Int = 0,
    var success: Boolean = false
) {
    data class Data(
        @SerializedName("payment_url")
        var paymentUrl: String = ""
    )
}