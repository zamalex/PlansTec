package creativitysol.com.planstech.home.model


import com.google.gson.annotations.SerializedName

data class ArticlesModel(
    var `data`: List<Data> = listOf(),
    var message: String = "",
    @SerializedName("status_code")
    var statusCode: Int = 0
) {
    data class Data(
        @SerializedName("created_at")
        var createdAt: String = "",
        var description: String = "",
        var id: Int = 0,
        var image: String = "",
        var status: String = "",
        var title: String = "",
        @SerializedName("updated_at")
        var updatedAt: String = ""
    )
}