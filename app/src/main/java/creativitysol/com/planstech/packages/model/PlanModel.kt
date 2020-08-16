package creativitysol.com.planstech.packages.model


import com.google.gson.annotations.SerializedName

data class PlanModel(
    var `data`: List<Data> = listOf(),
    var message: String = "",
    @SerializedName("status_code")
    var statusCode: Int = 0
) {
    data class Data(
        @SerializedName("completed_days")
        var completedDays: String = "",
        @SerializedName("created_at")
        var createdAt: String = "",
        var goals: String = "",
        var id: Int = 0,
        var price: String = "",
        var title: String = "",
        @SerializedName("updated_at")
        var updatedAt: String = ""
    )
}