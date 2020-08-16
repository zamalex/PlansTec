package creativitysol.com.planstech.home.model


import com.google.gson.annotations.SerializedName

data class TrainingModel(
    var `data`: List<Data> = listOf(),
    var message: String = "",
    @SerializedName("status_code")
    var statusCode: Int = 0
) {
    data class Data(
        var address: String = "",
        @SerializedName("created_at")
        var createdAt: String = "",
        var description: String = "",
        var duration: String = "",
        var id: Int = 0,
        var image: String = "",
        @SerializedName("num_seats")
        var numSeats: String = "",
        var price: String = "",
        var status: String = "",
        var title: String = "",
        var type: String = "",
        @SerializedName("updated_at")
        var updatedAt: String = ""
    )
}