package com.eneskkoc.shopcase.data.service

import com.eneskkoc.shopcase.util.Constant
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient ,gson: Gson): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(Constant.API_BASE_PATH)
        .client(okHttpClient)
        .build()
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideApiService(retrofit: Retrofit): CategoryApi = retrofit.create(CategoryApi::class.java)

    @Provides
    fun provideProductApiService(retrofit: Retrofit): ProductApi = retrofit.create(ProductApi::class.java)

    @Provides
    fun provideDetailApiService(retrofit: Retrofit): DetailApi = retrofit.create(DetailApi::class.java)


}