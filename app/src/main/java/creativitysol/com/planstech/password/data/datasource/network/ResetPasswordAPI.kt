package creativitysol.com.planstech.password.data.datasource.network

import creativitysol.com.planstech.password.data.model.GenericPasswordResult
import creativitysol.com.planstech.password.data.model.ResetPasswordBody
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ResetPasswordAPI {
    @POST("auth/reset_password")
    fun restPassword(@Body resetPass: ResetPasswordBody): Single<GenericPasswordResult>

}