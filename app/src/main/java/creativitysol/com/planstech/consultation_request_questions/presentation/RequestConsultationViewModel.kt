package creativitysol.com.planstech.consultation_request_questions.presentation

import androidx.lifecycle.ViewModel
import creativitysol.com.planstech.consultation_request_questions.data.model.RequestConsultationBody
import creativitysol.com.planstech.consultation_request_questions.domain.RequestConsultationUseCase

class RequestConsultationViewModel(
    private val requestConsultationUseCase:
    RequestConsultationUseCase
) : ViewModel() {

    fun requestConsultation(requestConsultationBody: RequestConsultationBody) {
        requestConsultationUseCase.execute(requestConsultationBody)
    }

    val error = requestConsultationUseCase.error
    val consultationStatus = requestConsultationUseCase.consultationHasSent

    override fun onCleared() {
        super.onCleared()
        requestConsultationUseCase.flushResources()
    }
}
