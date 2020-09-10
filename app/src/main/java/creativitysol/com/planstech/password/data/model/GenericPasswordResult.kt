package creativitysol.com.planstech.password.data.model

import com.google.gson.annotations.SerializedName

data class GenericPasswordResult(
    val data: Data? = null,
    @SerializedName("status_code")
    val statusCode: Long? = null,
    val message: String? = null
)

data class ForgotPasswordBody(
    val email: String
)

data class ResetPasswordBody(
    val password: String,
    @SerializedName("confirm_password")
    val confirmPassword: String,
    val code: String
)

data class Data(
    @SerializedName("verification_code")
    val verificationCode: String?
)

