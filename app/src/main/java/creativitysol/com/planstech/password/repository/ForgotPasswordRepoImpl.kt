package creativitysol.com.planstech.password.repository

import creativitysol.com.planstech.password.data.datasource.network.ForgotPasswordAPI
import creativitysol.com.planstech.password.data.model.ForgotPasswordBody
import creativitysol.com.planstech.password.data.model.GenericPasswordResult
import io.reactivex.rxjava3.core.Single

class ForgotPasswordRepoImpl(private val forgotPasswordAPI: ForgotPasswordAPI) :
    ForgotPasswordRepo {
    override fun forgetPassword(email: String): Single<GenericPasswordResult> {
        return forgotPasswordAPI.forgetPassword(email = ForgotPasswordBody(email))
    }
}