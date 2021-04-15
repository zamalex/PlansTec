package creativitysol.com.planstech.conschat.domain

import androidx.lifecycle.MutableLiveData
import creativitysol.com.planstech.base.Executors
import creativitysol.com.planstech.base.UseCase
import creativitysol.com.planstech.conschat.data.model.ChatMessages
import creativitysol.com.planstech.conschat.data.repository.ChatHistoryRepo
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ChatHistoryUseCase(
    private val compositeDisposable: CompositeDisposable,
    private val executors: Executors,
    private val chatHistoryRepo: ChatHistoryRepo
) : UseCase<Unit?, Unit> {

    override fun execute(value: Unit?) {
        compositeDisposable.add(
            chatHistoryRepo.getChatHistory()
                .subscribeOn(executors.getIOThread())
                .subscribe({
                    chatHistoryList.postValue(it)
                }, {
                    historyError.postValue(it)
                })
        )
    }

    override fun flushResources() {
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }

    val historyError = MutableLiveData<Throwable>()
    val chatHistoryList = MutableLiveData<ChatMessages>()
}