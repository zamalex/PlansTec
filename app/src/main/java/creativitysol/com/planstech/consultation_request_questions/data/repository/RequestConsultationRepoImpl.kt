package creativitysol.com.planstech.consultation_request_questions.data.repository

import creativitysol.com.planstech.consultation_request_questions.data.datasource.network.RequestConsultationAPI
import creativitysol.com.planstech.consultation_request_questions.data.model.RequestConsultationBody
import io.reactivex.rxjava3.core.Completable

class RequestConsultationRepoImpl(
    private val requestConsultationAPI: RequestConsultationAPI
) : RequestConsultationRepo {
    override fun requestConsultation(requestConsultationBody: RequestConsultationBody): Completable
            = requestConsultationAPI.requestConsultation(requestConsultationBody)
}