package creativitysol.com.planstech.home.model


import com.google.gson.annotations.SerializedName

data class ReviewsModel(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("status_code")
    var statusCode: Int = 0
) {
    data class Data(
        @SerializedName("current_page")
        var currentPage: Int = 0,
        @SerializedName("first_page_url")
        var firstPageUrl: String = "",
        @SerializedName("from")
        var from: Int = 0,
        @SerializedName("last_page")
        var lastPage: Int = 0,
        @SerializedName("last_page_url")
        var lastPageUrl: String = "",
        @SerializedName("next_page_url")
        var nextPageUrl: Any = Any(),
        @SerializedName("path")
        var path: String = "",
        @SerializedName("per_page")
        var perPage: Int = 0,
        @SerializedName("prev_page_url")
        var prevPageUrl: Any = Any(),
        @SerializedName("data")
        var reviews: List<Review> = listOf(),
        @SerializedName("to")
        var to: Int = 0,
        @SerializedName("total")
        var total: Int = 0
    ) {
        data class Review(
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
}