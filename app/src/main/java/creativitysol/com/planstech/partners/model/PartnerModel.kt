package creativitysol.com.planstech.partners.model


import com.google.gson.annotations.SerializedName

data class PartnerModel(
    @SerializedName("data")
    var `data`: List<Data> = listOf(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("status_code")
    var statusCode: Int = 0
) {
    data class Data(
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("logos")
        var logos: List<String> = listOf(),
        @SerializedName("title")
        var title: String = ""
    )
}