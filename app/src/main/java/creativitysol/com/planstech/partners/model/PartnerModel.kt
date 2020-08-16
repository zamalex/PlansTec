package creativitysol.com.planstech.partners.model


import com.google.gson.annotations.SerializedName

data class PartnerModel(
    var `data`: List<Data> = listOf(),
    var message: String = "",
    @SerializedName("status_code")
    var statusCode: Int = 0
) {
    data class Data(
        @SerializedName("created_at")
        var createdAt: String = "",
        var id: Int = 0,
        var logos: List<Logo> = listOf(),
        var title: String = "",
        @SerializedName("updated_at")
        var updatedAt: String = ""
    ) {
        data class Logo(
            @SerializedName("created_at")
            var createdAt: String = "",
            var id: String = "",
            var logo: String = "",
            @SerializedName("partner_id")
            var partnerId: String = "",
            @SerializedName("updated_at")
            var updatedAt: String = ""
        )
    }
}