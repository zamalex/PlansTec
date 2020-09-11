package creativitysol.com.planstech.password.di

import creativitysol.com.planstech.password.data.datasource.network.ResetPasswordAPI
import creativitysol.com.planstech.password.domain.ResetPasswordUseCase
import creativitysol.com.planstech.password.presentation.ResetPasswordViewModel
import creativitysol.com.planstech.password.data.repository.ResetPasswordRepo
import creativitysol.com.planstech.password.data.repository.ResetPasswordRepoImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit


val resetPassModule = module {
    single { get<Retrofit>().create(ResetPasswordAPI::class.java) }
    factory<ResetPasswordRepo> { ResetPasswordRepoImpl(get()) }
    factory { ResetPasswordUseCase(get(), get(), get()) }
    viewModel { ResetPasswordViewModel(get()) }
}