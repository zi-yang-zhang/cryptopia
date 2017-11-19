package com.cryptopia.android

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber

/**
 * Created by ziyang.zhang on 2017-11-18.
 */

@Module
class NetworkModule {
    @Provides
    fun retrofit(client: OkHttpClient): Retrofit = Retrofit
            .Builder()
            .baseUrl(BuildConfig.CRYPTO_COMPARE_API)
            .client(client)
            .build()

    @Provides
    fun httpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor({ log -> Timber.d(log) })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient
                .Builder()
                .addInterceptor(loggingInterceptor)
                .build()

    }


}
