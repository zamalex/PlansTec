package creativitysol.com.planstech.password.data.repository

import creativitysol.com.planstech.password.data.datasource.network.ResetPasswordAPI
import creativitysol.com.planstech.password.data.model.GenericPasswordResult
import creativitysol.com.planstech.password.data.model.ResetPasswordBody
import io.reactivex.rxjava3.core.Single

class ResetPasswordRepoImpl(private val resetPasswordAPI: ResetPasswordAPI) : ResetPasswordRepo {
    override fun resetPassword(resetPasswordBody: ResetPasswordBody): Single<GenericPasswordResult> {
        return resetPasswordAPI.restPassword(resetPass = resetPasswordBody)
    }
}