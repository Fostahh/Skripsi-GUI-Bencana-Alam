package com.mohammadazri.gui_bencana_alam.core.di

import com.mohammadazri.gui_bencana_alam.core.data.Repository
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.IRemoteDataSource
import com.mohammadazri.gui_bencana_alam.core.data.source.remote.RemoteDataSource
import com.mohammadazri.gui_bencana_alam.core.domain.repository.IRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRemoteDataSource(remoteDataSource: RemoteDataSource): IRemoteDataSource

    @Binds
    abstract fun provideRepository(repository: Repository): IRepository
}