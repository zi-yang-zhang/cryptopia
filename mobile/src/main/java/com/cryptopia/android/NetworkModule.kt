package com.cryptopia.android

import com.cryptopia.android.network.CryptoCompareAPI
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

/**
 * Created by ziyang.zhang on 2017-11-18.
 */

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun retrofit(client: OkHttpClient): Retrofit = Retrofit
            .Builder()
            .baseUrl(BuildConfig.CRYPTO_COMPARE_API)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    fun httpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor({ log -> Timber.d(log) })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient
                .Builder()
                .addInterceptor(loggingInterceptor)
                .build()

    }

    @Provides
    fun crytoCompareApi(retrofit: Retrofit): CryptoCompareAPI = retrofit.create(CryptoCompareAPI::class.java)


}
