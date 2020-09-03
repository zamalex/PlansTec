package creativitysol.com.planstech.login.model


import com.google.gson.annotations.SerializedName

data class LoginModel(
    var `data`: Data = Data(),
    var message: String = "",
    @SerializedName("status_code")
    var statusCode: Int = 0
) {
    data class Data(
        @SerializedName("expires_in")
        var expiresIn: Int = 0,
        var token: String = "",
        @SerializedName("token_type")
        var tokenType: String = ""
    )
}