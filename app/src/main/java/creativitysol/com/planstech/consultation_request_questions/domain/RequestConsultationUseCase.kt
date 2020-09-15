package creativitysol.com.planstech.consultation_request_questions.domain

import androidx.lifecycle.MutableLiveData
import creativitysol.com.planstech.base.Executors
import creativitysol.com.planstech.base.UseCase
import creativitysol.com.planstech.consultation_request_questions.data.model.RequestConsultationBody
import creativitysol.com.planstech.consultation_request_questions.data.repository.RequestConsultationRepo
import io.reactivex.rxjava3.disposables.CompositeDisposable

class RequestConsultationUseCase(
    private val requestConsultationRepo: RequestConsultationRepo,
    private val compositeDisposable: CompositeDisposable,
    private val executors: Executors
) : UseCase<RequestConsultationBody, Unit> {


    override fun execute(value: RequestConsultationBody) {
        compositeDisposable.add(
            requestConsultationRepo.requestConsultation(value)
                .subscribeOn(executors.getIOThread())
                .subscribe({
                    consultationHasSent.postValue(true)
                },{
                    error.postValue(it)
                })
        )
    }

    override fun flushResources() {
        if(!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }

    val consultationHasSent = MutableLiveData<Boolean>()
    val error = MutableLiveData<Throwable>()
}