package creativitysol.com.planstech.consultation_request_questions.data.repository

import creativitysol.com.planstech.consultation_request_questions.data.model.RequestConsultationBody
import io.reactivex.rxjava3.core.Completable

interface RequestConsultationRepo {
    fun requestConsultation(requestConsultationBody: RequestConsultationBody): Completable
}