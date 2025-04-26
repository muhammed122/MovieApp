package com.example.utils.di

import com.example.utils.resourceprovider.IResourceProvider
import com.example.utils.resourceprovider.ResourceProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ResourceModule {
    @Binds
    abstract fun bindResourceProvider(resourceProvider: ResourceProvider): IResourceProvider
}