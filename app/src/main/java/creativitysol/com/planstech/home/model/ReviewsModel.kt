package creativitysol.com.planstech.home.model


import com.google.gson.annotations.SerializedName

data class ReviewsModel(
    var `data`: List<Data> = listOf(),
    var message: String = "",
    @SerializedName("status_code")
    var statusCode: Int = 0
) {
    data class Data(
        var avatar: String = "",
        var city: String = "",
        @SerializedName("created_at")
        var createdAt: String = "",
        var district: String = "",
        var email: String = "",
        @SerializedName("email_verified_at")
        var emailVerifiedAt: Any = Any(),
        var id: Int = 0,
        @SerializedName("is_verified")
        var isVerified: String = "",
        var name: String = "",
        var password: String = "",
        @SerializedName("remember_token")
        var rememberToken: Any = Any(),
        var review: String = "",
        var role: String = "",
        var stars: String = "",
        @SerializedName("subscription_plans")
        var subscriptionPlans: String = "",
        @SerializedName("updated_at")
        var updatedAt: String = "",
        @SerializedName("user_id")
        var userId: String = "",
        var usertype: String = ""
    )
}