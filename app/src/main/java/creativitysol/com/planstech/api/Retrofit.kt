package creativitysol.com.planstech.api

import creativitysol.com.planstech.login.model.LoginModel
import creativitysol.com.planstech.util.Constants
import io.paperdb.Paper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Retrofit {
    val log =  Paper.book().read("login", LoginModel())

    private val logging =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY

        }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .readTimeout(90, TimeUnit.SECONDS)
        .connectTimeout(90, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                     .addHeader("Authorization", "Bearer ${log.data.token}")
                    .build()
            )
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val Api: ApiService by lazy {
        retrofit.create(
            ApiService::class.java
        )
    }
}