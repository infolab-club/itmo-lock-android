package club.infolab.itmo_lock.di

import club.infolab.itmo_lock.config.AppConfig.Companion.BASE_URL
import club.infolab.itmo_lock.data.network.ItmoLockService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

val contentType = "application/json".toMediaType()

val networkModule = module {
    factory {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    factory { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }
    single { provideService(get()) }
}

@OptIn(ExperimentalSerializationApi::class)
fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()
}

fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build()
}

fun provideService(retrofit: Retrofit): ItmoLockService =
    retrofit.create(ItmoLockService::class.java)
