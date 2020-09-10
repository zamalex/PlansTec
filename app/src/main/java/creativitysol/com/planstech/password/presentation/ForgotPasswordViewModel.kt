package creativitysol.com.planstech.password.presentation

import androidx.lifecycle.ViewModel
import creativitysol.com.planstech.password.domain.ForgotPasswordUseCase

class ForgotPasswordViewModel(
    private val forgotPasswordUseCase:
    ForgotPasswordUseCase
) : ViewModel() {

    fun forgotPassword(email: String) {
        forgotPasswordUseCase.execute(email = email)
    }

    val verificationCode = forgotPasswordUseCase.verificationCode
    val error = forgotPasswordUseCase.error

    override fun onCleared() {
        super.onCleared()
        forgotPasswordUseCase.flushResources()
    }
}
