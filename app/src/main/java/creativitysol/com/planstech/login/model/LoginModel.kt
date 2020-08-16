package creativitysol.com.planstech.login.model


import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("access_token")
    var accessToken: String = "",
    @SerializedName("expires_in")
    var expiresIn: Int = 0,
    @SerializedName("token_type")
    var tokenType: String = ""
)