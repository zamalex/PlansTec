package creativitysol.com.planstech.profile


import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("status_code")
    var statusCode: Int = 0
) {
    data class Data(
        @SerializedName("avatar")
        var avatar: String = "",
        @SerializedName("city")
        var city: String = "",
        @SerializedName("created_at")
        var createdAt: String = "",
        @SerializedName("district")
        var district: String = "",
        @SerializedName("email")
        var email: String = "",
        @SerializedName("email_verified_at")
        var emailVerifiedAt: Any = Any(),
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("is_verified")
        var isVerified: String = "",
        @SerializedName("name")
        var name: String = "",
        @SerializedName("phone")
        var phone: String = "",
        @SerializedName("role")
        var role: String = "",
        @SerializedName("subscription_plans")
        var subscriptionPlans: String = "",
        @SerializedName("support_chat")
        var supportChat: String = "",
        @SerializedName("updated_at")
        var updatedAt: String = "",
        @SerializedName("usertype")
        var usertype: String = "",
        @SerializedName("verification_code")
        var verificationCode: String = ""
    )
}