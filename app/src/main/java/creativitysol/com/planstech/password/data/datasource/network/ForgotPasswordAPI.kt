package creativitysol.com.planstech.password.data.datasource.network

import creativitysol.com.planstech.password.data.model.ForgotPasswordBody
import creativitysol.com.planstech.password.data.model.GenericPasswordResult
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ForgotPasswordAPI {
    @POST("auth/forget_password")
    fun forgetPassword(@Body email: ForgotPasswordBody): Single<GenericPasswordResult>
}