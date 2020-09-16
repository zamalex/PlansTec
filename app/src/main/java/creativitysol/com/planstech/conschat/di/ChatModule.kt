package creativitysol.com.planstech.conschat.di

import creativitysol.com.planstech.conschat.data.datasource.network.ChatHistoryAPI
import creativitysol.com.planstech.conschat.data.datasource.network.SendChatAPI
import creativitysol.com.planstech.conschat.data.repository.ChatHistoryRepo
import creativitysol.com.planstech.conschat.data.repository.ChatHistoryRepoImpl
import creativitysol.com.planstech.conschat.data.repository.SendChatRepo
import creativitysol.com.planstech.conschat.data.repository.SendChatRepoImpl
import creativitysol.com.planstech.conschat.domain.ChatHistoryUseCase
import creativitysol.com.planstech.conschat.domain.SendChatUseCase
import creativitysol.com.planstech.conschat.presentation.ConsultationChatViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val chatModule = module {
    single { get<Retrofit>().create(SendChatAPI::class.java) }
    single { get<Retrofit>().create(ChatHistoryAPI::class.java) }
    factory<ChatHistoryRepo> { ChatHistoryRepoImpl(get()) }
    factory<SendChatRepo> { SendChatRepoImpl(get()) }
    factory { ChatHistoryUseCase(get(), get(), get()) }
    factory { SendChatUseCase(get(), get(), get()) }
    viewModel { ConsultationChatViewModel(get(), get()) }
}