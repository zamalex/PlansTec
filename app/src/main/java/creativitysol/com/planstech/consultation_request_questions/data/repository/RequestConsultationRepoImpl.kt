package creativitysol.com.planstech.consultation_request_questions.data.repository

import creativitysol.com.planstech.consultation_request_questions.data.datasource.network.RequestConsultationAPI
import creativitysol.com.planstech.consultation_request_questions.data.model.RequestConsultationBody
import creativitysol.com.planstech.login.model.LoginModel
import io.paperdb.Paper
import io.reactivex.rxjava3.core.Completable

class RequestConsultationRepoImpl(
    private val requestConsultationAPI: RequestConsultationAPI
) : RequestConsultationRepo {
    override fun requestConsultation(requestConsultationBody: RequestConsultationBody): Completable
            = requestConsultationAPI.requestConsultation(requestConsultationBody,"Bearer ${(Paper.book().read("login",
        LoginModel()
    )as LoginModel).data.token}")
}