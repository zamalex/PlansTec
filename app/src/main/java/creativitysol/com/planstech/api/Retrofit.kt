package creativitysol.com.planstech.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {


    val logging: HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://creativitysol.com/planstec/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val Api: ApiService by lazy {
        retrofit.create(
            ApiService::class.java
        )
    }
}