package creativitysol.com.planstech.password.domain

import androidx.lifecycle.MutableLiveData
import creativitysol.com.planstech.base.Executors
import creativitysol.com.planstech.base.UseCase
import creativitysol.com.planstech.password.data.model.GenericPasswordResult
import creativitysol.com.planstech.password.data.model.ResetPasswordBody
import creativitysol.com.planstech.password.repository.ResetPasswordRepo
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ResetPasswordUseCase(
    private val executors: Executors,
    private val resetPasswordRepo: ResetPasswordRepo,
    private val compositeDisposable: CompositeDisposable,
) : UseCase<ResetPasswordBody, Any> {

    override fun execute(resetPasswordBody: ResetPasswordBody) {
        compositeDisposable.add(
            resetPasswordRepo.resetPassword(resetPasswordBody = resetPasswordBody)
                .subscribeOn(executors.getIOThread())
                .subscribe({
                    if (it != null)
                        resetResult.postValue(it)
                }, { error.postValue(it) })
        )
    }

    override fun flushResources() {
        if (compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }

    val resetResult = MutableLiveData<GenericPasswordResult>()
    val error = MutableLiveData<Throwable>()
}