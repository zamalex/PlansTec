package creativitysol.com.planstech.coursedetails.model


import com.google.gson.annotations.SerializedName

data class SingleCourse(
    var `data`: Data = Data(),
    var message: String = "",
    @SerializedName("status_code")
    var statusCode: Int = 0
) {
    data class Data(
        var address: String = "",
        @SerializedName("created_at")
        var createdAt: String = "",
        @SerializedName("is_subscribe")
        var isSubscribed: Boolean = false,
        var description: String = "",
        var duration: String = "",
        var id: Int = 0,
        var image: String = "",
        @SerializedName("images_gallary")
        var imagesGallary: List<ImagesGallary> = listOf(),
        @SerializedName("num_seats")
        var numSeats: String = "",
        var price: Int = 0,
        var status: String = "",
        var title: String = "",
        var type: String = "",
        var fav: String = "",
        @SerializedName("updated_at")
        var updatedAt: String = ""
    ) {
        data class ImagesGallary(
            @SerializedName("created_at")
            var createdAt: String = "",
            var id: String = "",
            var image: String = "",
            @SerializedName("training_id")
            var trainingId: String = "",
            @SerializedName("updated_at")
            var updatedAt: String = ""
        )
    }
}