package com.mohammadazri.gui_bencana_alam.di

import com.mohammadazri.gui_bencana_alam.core.data.Repository
import com.mohammadazri.gui_bencana_alam.core.domain.IRepository
import com.mohammadazri.gui_bencana_alam.core.domain.usecase.Interactor
import com.mohammadazri.gui_bencana_alam.core.domain.usecase.UseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    @ViewModelScoped
    abstract fun provideUseCase(interactor: Interactor): UseCase
}