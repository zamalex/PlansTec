package creativitysol.com.planstech.register.model


import com.google.gson.annotations.SerializedName

data class RegisterModel(
    var `data`: Data = Data(),
    var msg: String = "",
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
        var id: Int = 0,
        var name: String = "",
        var usertype: String = "",
        var role: String = "",
        var is_verified: String = "",
        var subscription_plans: String = "",
        @SerializedName("updated_at")
        var updatedAt: String = "",
        @SerializedName("phone")
        var phone: String = ""
    )
}