package creativitysol.com.planstech.gladtoserve.model


import com.google.gson.annotations.SerializedName

data class Services(
    var `data`: List<Data> = listOf(),
    var message: String = "",
    @SerializedName("status_code")
    var statusCode: Int = 0
) {
    data class Data(
        @SerializedName("created_at")
        var createdAt: String = "",
        var id: Int = 0,
        var title: String = "",
        @SerializedName("updated_at")
        var updatedAt: String = ""
    )
}