package creativitysol.com.planstech.conschat.domain

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import creativitysol.com.planstech.base.Executors
import creativitysol.com.planstech.base.UseCase
import creativitysol.com.planstech.conschat.data.model.SenderBody
import creativitysol.com.planstech.conschat.data.repository.SendChatRepo
import io.reactivex.rxjava3.disposables.CompositeDisposable

class SendChatUseCase(
    private val compositeDisposable: CompositeDisposable,
    private val executors: Executors,
    private val sendChatRepo: SendChatRepo
) : UseCase<SenderBody, Unit> {

    override fun execute(value: SenderBody) {
        compositeDisposable.add(
            sendChatRepo.sendChatMessage(value)
                .subscribeOn(executors.getIOThread())
                .subscribe({
                    sendMessageStatus.postValue(true)
                }, {
                    sendError.postValue(it)
                })
        )
    }

    override fun flushResources() {
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }

    val sendError = MutableLiveData<Throwable>()
    val sendMessageStatus = MutableLiveData<Boolean>()
}