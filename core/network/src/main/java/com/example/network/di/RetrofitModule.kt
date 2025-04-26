package com.example.network.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.network.BuildConfig
import com.example.network.network.adapter.ApiCallAdapterFactory
import com.example.network.utils.Headers
import com.example.utils.resourceprovider.IResourceProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val REQUEST_TIME_OUT: Long = 60

private const val BASE_URL = "https://api.themoviedb.org/3/"

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideHeaderInterceptor(resourceProvider: IResourceProvider): Interceptor =
        Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader(Headers.Keys.ACCEPT, Headers.Values.ACCEPT_VALUE)
                .addHeader(Headers.Keys.CONTENT_TYPE, Headers.Values.ACCEPT_VALUE)
                .addHeader(Headers.Keys.ACCEPT_LANGUAGE, resourceProvider.getLocale().language)
                .addHeader(Headers.Keys.AUTHORIZATION, BuildConfig.AUTH_TOKEN)
            chain.proceed(newRequest.build())

        }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Singleton
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor =
        ChuckerInterceptor.Builder(context).redactHeaders(Headers.Keys.AUTHORIZATION).build()

    @Provides
    @Singleton
    fun provideHttpClientBuilder(
        headersInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
    ): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder().readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS).addInterceptor(headersInterceptor)
        builder.addNetworkInterceptor(loggingInterceptor)
        builder.addNetworkInterceptor(chuckerInterceptor)


        return builder
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(okHttpClientBuilder: OkHttpClient.Builder): OkHttpClient =
        okHttpClientBuilder.build()

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setLenient()
//            .serializeNulls()
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        gson: Gson, apiCallAdapterFactory: ApiCallAdapterFactory,
    ): Retrofit.Builder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(apiCallAdapterFactory)


    @Provides
    @Singleton
    fun provideRetrofit(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        // Fetch the base URL from BaseUrlProvider
        return retrofitBuilder.client(okHttpClient).baseUrl(BASE_URL).build()
    }


    @Provides
    @Singleton
    fun provideApiCallFactory(resourceProvider: IResourceProvider) =
        ApiCallAdapterFactory(resourceProvider)
}

