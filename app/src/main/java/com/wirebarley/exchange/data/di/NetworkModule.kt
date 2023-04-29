package com.wirebarley.exchange.data.di

import com.wirebarley.exchange.BuildConfig.API_KEY
import com.wirebarley.exchange.data.source.remote.api.CurrencyLayerAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideCurrencyLayerAPI(@CurrencyLayerClient okHttpClient: OkHttpClient): CurrencyLayerAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.apilayer.com/")
            .client(okHttpClient)
            .build()
            .create(CurrencyLayerAPI::class.java)
    }

    @Singleton
    @Provides
    @CurrencyLayerClient
    fun provideCurrencyLayerOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(CurrencyLayerQueryParameterInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    class CurrencyLayerQueryParameterInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url
            API_KEY
            val modifiedRequest = originalRequest.newBuilder()
                .addHeader("apikey", "8PH7pBvz48f7IXFJ1xxNDaoKKBCGX7jm")
                // local.properties에서 api key 보호
//                .addHeader("apikey", API_KEY)
                .url(originalUrl)
                .build()

            return chain.proceed(modifiedRequest)
        }
    }

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CurrencyLayerClient