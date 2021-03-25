package creativitysol.com.planstech.home.model


import com.google.gson.annotations.SerializedName

data class TrainingModel(
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
        @SerializedName("to")
        var to: Int = 0,
        @SerializedName("total")
        var total: Int = 0,
        @SerializedName("data")
        var trainings: List<Training> = listOf()
    ) {
        data class Training(
            @SerializedName("address")
            var address: String = "",
            @SerializedName("created_at")
            var createdAt: String = "",
            @SerializedName("description")
            var description: String = "",
            @SerializedName("duration")
            var duration: String = "",
            @SerializedName("fav")
            var fav: Int = 0,
            @SerializedName("id")
            var id: Int = 0,
            @SerializedName("image")
            var image: String = "",
            @SerializedName("images_gallery")
            var imagesGallery: List<ImagesGallery> = listOf(),
            @SerializedName("is_free")
            var isFree: Boolean = false,
            @SerializedName("is_subscribe")
            var isSubscribe: Boolean = false,
            @SerializedName("num_seats")
            var numSeats: Int = 0,
            @SerializedName("price")
            var price: Int = 0,
            @SerializedName("status")
            var status: String = "",
            @SerializedName("title")
            var title: String = "",
            @SerializedName("type")
            var type: String = "",
            @SerializedName("updated_at")
            var updatedAt: String = ""
        ) {
            data class ImagesGallery(
                @SerializedName("created_at")
                var createdAt: String = "",
                @SerializedName("id")
                var id: Int = 0,
                @SerializedName("image")
                var image: String = "",
                @SerializedName("training_id")
                var trainingId: Int = 0,
                @SerializedName("updated_at")
                var updatedAt: String = ""
            )
        }
    }
}