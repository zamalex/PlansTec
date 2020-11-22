package creativitysol.com.planstech.packages.model


import com.google.gson.annotations.SerializedName

data class PlanModel(
    var `data`: List<Data> = listOf(),
    @SerializedName("status_code")
    var statusCode: Int = 0,
    var success: Boolean = false
) {
    data class Data(
        @SerializedName("completed_days")
        var completedDays: Int = 0,
        var goals: String = "",
        var id: Int = 0,
        var price: Int = 0,
        @SerializedName("price_text")
        var priceText: String = "",
        var title: String = "",
        var is_subscribed : Int=0,
        var can_subscribe : Int=0,
        @SerializedName("is_free")
        var is_free : Boolean=false,
        var try_subscription : Int=0
    )
}