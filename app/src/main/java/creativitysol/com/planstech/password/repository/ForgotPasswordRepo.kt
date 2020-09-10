package creativitysol.com.planstech.password.repository

import creativitysol.com.planstech.password.data.model.GenericPasswordResult
import io.reactivex.rxjava3.core.Single

interface ForgotPasswordRepo {
    fun forgetPassword(email: String): Single<GenericPasswordResult>
}