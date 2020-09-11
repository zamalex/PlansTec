import creativitysol.com.planstech.favorites.data.datasource.network.ListFavouritesAPI
import creativitysol.com.planstech.favorites.data.repository.ListFavouriteRepo
import creativitysol.com.planstech.favorites.data.repository.ListFavouriteRepoImpl
import creativitysol.com.planstech.favorites.domain.ListFavouritesUseCase
import creativitysol.com.planstech.favorites.presentation.viewmodel.ListFavouritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val listFavouritesModule = module {

    single { get<Retrofit>().create(ListFavouritesAPI::class.java) }
    factory<ListFavouriteRepo> { ListFavouriteRepoImpl(get()) }
    factory { ListFavouritesUseCase(get(), get(), get()) }
    viewModel { ListFavouritesViewModel(get())  }
}