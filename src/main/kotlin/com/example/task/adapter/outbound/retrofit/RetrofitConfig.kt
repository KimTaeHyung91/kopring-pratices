package com.example.task.adapter.outbound.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

@Configuration
class RetrofitConfig(
    // kotlin 에서는 이스케이프 문자 앞에 붙여줘야됨.
    @Value("\${integration.store.base-url}")
    private val integrationStoreApiUrl: String,
) {
    @Bean
    fun okHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

    @Bean
    fun retrofitFakeStoreApi(okHttpClient: OkHttpClient): RetrofitFakeStoreApi =
        Retrofit.Builder()
            .baseUrl(integrationStoreApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create()
}
