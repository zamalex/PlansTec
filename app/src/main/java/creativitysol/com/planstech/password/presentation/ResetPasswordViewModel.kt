package creativitysol.com.planstech.password.presentation

import androidx.lifecycle.ViewModel
import creativitysol.com.planstech.password.data.model.ResetPasswordBody
import creativitysol.com.planstech.password.domain.ResetPasswordUseCase

class ResetPasswordViewModel(
    private val resetPasswordUseCase:
    ResetPasswordUseCase
) : ViewModel() {

    fun resetPassword(resetPasswordBody: ResetPasswordBody) {
        resetPasswordUseCase.execute(resetPasswordBody = resetPasswordBody)
    }

    val verificationCode = resetPasswordUseCase.resetResult
    val error = resetPasswordUseCase.error

    override fun onCleared() {
        super.onCleared()
        resetPasswordUseCase.flushResources()
    }
}
