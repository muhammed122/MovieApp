package com.example.movieapptask.di

import com.example.movieapptask.data.repository.RepositoryImpl
import com.example.movieapptask.data.source.datasource.RemoteDataSource
import com.example.movieapptask.data.source.datasource.RemoteDataSourceImpl
import com.example.movieapptask.data.source.remote.ApiService
import com.example.movieapptask.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAuthServices(
        retrofit: Retrofit,
    ): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class BindAppModule {
    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(
        dataSourceImpl: RemoteDataSourceImpl,
    ): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindRepository(
        repositoryImpl: RepositoryImpl,
    ): Repository
}