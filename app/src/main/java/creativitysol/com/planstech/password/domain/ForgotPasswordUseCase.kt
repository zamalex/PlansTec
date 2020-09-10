package creativitysol.com.planstech.password.domain

import androidx.lifecycle.MutableLiveData
import creativitysol.com.planstech.base.Executors
import creativitysol.com.planstech.base.UseCase
import creativitysol.com.planstech.password.repository.ForgotPasswordRepo
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.lang.Exception
import java.net.UnknownHostException

class ForgotPasswordUseCase(
    private val executors: Executors,
    private val forgotPasswordRepo: ForgotPasswordRepo,
    private val compositeDisposable: CompositeDisposable,
) : UseCase<String, Any> {

    override fun execute(email: String){
        compositeDisposable.
                add(forgotPasswordRepo.forgetPassword(email)
                    .subscribeOn(executors.getIOThread())
                    .subscribe({
                        if(it?.data!=null)
                            verificationCode.postValue(it.data.verificationCode)
                    },{ error.postValue(it) })
                )
    }

    override fun flushResources() {
        if(compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }

    val verificationCode = MutableLiveData<String>()
    val error = MutableLiveData<Throwable>()
}