package creativitysol.com.planstech.articledetails.model


import com.google.gson.annotations.SerializedName

data class SingleArticle(
    var `data`: Data = Data(),
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