package creativitysol.com.planstech.consultation_request_questions.data.datasource.network

import creativitysol.com.planstech.consultation_request_questions.data.model.RequestConsultationBody
import io.reactivex.rxjava3.core.Completable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RequestConsultationAPI {

    @POST("auth/request_consultations")
    fun requestConsultation(
        @Body requestConsultationBody : RequestConsultationBody
    ,@Header("Authorization")token:String
    ) : Completable
}