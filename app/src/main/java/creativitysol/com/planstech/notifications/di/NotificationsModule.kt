package creativitysol.com.planstech.notifications.di

import creativitysol.com.planstech.notifications.data.datasource.network.NotificationsAPI
import creativitysol.com.planstech.notifications.data.repository.NotificationsRepo
import creativitysol.com.planstech.notifications.data.repository.NotificationsRepoImpl
import creativitysol.com.planstech.notifications.domain.NotificationsUseCase
import creativitysol.com.planstech.notifications.presentation.NotificationsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val notificationsModule = module {
    single { get<Retrofit>().create(NotificationsAPI::class.java) }
    factory<NotificationsRepo> { NotificationsRepoImpl(get()) }
    factory {
        NotificationsUseCase(
            compositeDisposable = get(),
            executors = get(),
            notificationsRepo = get()
        )
    }
    viewModel { NotificationsViewModel(get()) }
}