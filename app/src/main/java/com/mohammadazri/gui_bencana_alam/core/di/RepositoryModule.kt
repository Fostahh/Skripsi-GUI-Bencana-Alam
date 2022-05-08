package com.mohammadazri.gui_bencana_alam.core.di

import com.mohammadazri.gui_bencana_alam.core.data.Repository
import com.mohammadazri.gui_bencana_alam.core.domain.IRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repository: Repository): IRepository
}