package creativitysol.com.planstech.home.model


import com.google.gson.annotations.SerializedName

data class ReviewsModel(
    @SerializedName("data")
    var `data`: List<Data> = listOf(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("status_code")
    var statusCode: Int = 0
) {
    data class Data(
        @SerializedName("avatar")
        var avatar: String = "",
        @SerializedName("content")
        var content: String = "",
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("stars")
        var stars: Int = 0,
        @SerializedName("user_name")
        var userName: String = ""
    )
}