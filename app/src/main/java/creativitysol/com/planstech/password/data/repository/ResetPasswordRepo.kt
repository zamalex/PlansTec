package creativitysol.com.planstech.password.data.repository

import creativitysol.com.planstech.password.data.model.GenericPasswordResult
import creativitysol.com.planstech.password.data.model.ResetPasswordBody
import io.reactivex.rxjava3.core.Single

interface ResetPasswordRepo {
    fun resetPassword(resetPasswordBody: ResetPasswordBody): Single<GenericPasswordResult>
}