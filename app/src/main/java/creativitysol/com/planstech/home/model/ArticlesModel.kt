package creativitysol.com.planstech.home.model


import com.google.gson.annotations.SerializedName

data class ArticlesModel(
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
        @SerializedName("data")
        var articles: List<Article> = listOf(),
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
        @SerializedName("to")
        var to: Int = 0,
        @SerializedName("total")
        var total: Int = 0
    ) {
        data class Article(
            @SerializedName("created_at")
            var createdAt: String = "",
            @SerializedName("description")
            var description: String = "",
            @SerializedName("fav")
            var fav: Int = 0,
            @SerializedName("id")
            var id: Int = 0,
            @SerializedName("image")
            var image: String = "",
            @SerializedName("status")
            var status: Int = 0,
            @SerializedName("title")
            var title: String = "",
            @SerializedName("updated_at")
            var updatedAt: String = ""
        )
    }
}