import creativitysol.com.planstech.favorites.data.datasource.network.AddToFavouriteAPI
import creativitysol.com.planstech.favorites.data.repository.AddToFavouriteRepo
import creativitysol.com.planstech.favorites.data.repository.AddToFavouriteRepoImpl
import creativitysol.com.planstech.favorites.domain.AddToFavouritesUseCase
import creativitysol.com.planstech.favorites.presentation.viewmodel.AddToFavouritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val addToFavouritesModule = module {

    single { get<Retrofit>().create(AddToFavouriteAPI::class.java) }
    factory<AddToFavouriteRepo> { AddToFavouriteRepoImpl(get()) }
    factory { AddToFavouritesUseCase(get(), get(), get()) }
    viewModel { AddToFavouritesViewModel(get())  }
}