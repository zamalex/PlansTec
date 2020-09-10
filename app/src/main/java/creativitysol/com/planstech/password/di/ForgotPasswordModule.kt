package creativitysol.com.planstech.password.di

import creativitysol.com.planstech.password.data.datasource.network.ForgotPasswordAPI
import creativitysol.com.planstech.password.domain.ForgotPasswordUseCase
import creativitysol.com.planstech.password.presentation.ForgotPasswordViewModel
import creativitysol.com.planstech.password.repository.ForgotPasswordRepo
import creativitysol.com.planstech.password.repository.ForgotPasswordRepoImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit


val forgotPassModule = module {
    single { get<Retrofit>().create(ForgotPasswordAPI::class.java) }
    factory<ForgotPasswordRepo> { ForgotPasswordRepoImpl(get()) }
    factory { ForgotPasswordUseCase(get(), get(), get()) }
    viewModel { ForgotPasswordViewModel(get()) }
}