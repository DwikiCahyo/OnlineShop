package jihan.binar.pra_project_final.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


    @Module
    @InstallIn(SingletonComponent::class)
    object ApiClient {

        const val BASE_URL = "https://646b1d797d3c1cae4ce33622.mockapi.io/"

        private val logging: HttpLoggingInterceptor
            get() {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                return httpLoggingInterceptor.apply {
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                }
            }

        private val client = OkHttpClient.Builder().addInterceptor(logging).build()

        @Singleton
        @Provides
        fun provideRetrofit(): Retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        @Singleton
        @Provides
        fun provideUserApi(retrofit: Retrofit): ApiService =
            retrofit.create(ApiService::class.java)
    }
