package creativitysol.com.planstech.consultation_request_questions.di

import creativitysol.com.planstech.consultation_request_questions.data.datasource.network.RequestConsultationAPI
import creativitysol.com.planstech.consultation_request_questions.data.repository.RequestConsultationRepo
import creativitysol.com.planstech.consultation_request_questions.data.repository.RequestConsultationRepoImpl
import creativitysol.com.planstech.consultation_request_questions.domain.RequestConsultationUseCase
import creativitysol.com.planstech.consultation_request_questions.presentation.RequestConsultationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val requestConsultationModule = module {

    single { get<Retrofit>().create(RequestConsultationAPI::class.java) }

    factory<RequestConsultationRepo> { RequestConsultationRepoImpl(get()) }

    factory { RequestConsultationUseCase(get(), get(), get()) }

    viewModel { RequestConsultationViewModel (get()) }

}