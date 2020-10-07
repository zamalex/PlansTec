package creativitysol.com.planstech.about


import com.google.gson.annotations.SerializedName

data class DataResponse(
    var `data`: Data = Data(),
    @SerializedName("status_code")
    var statusCode: Int = 0,
    var success: Boolean = false
) {
    data class Data(
        var content: String = "",
        var title: String = ""
    )
}