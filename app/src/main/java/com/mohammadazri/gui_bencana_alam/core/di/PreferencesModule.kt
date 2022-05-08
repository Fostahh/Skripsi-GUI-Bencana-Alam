package com.citiasia.blanjaloka.core.di

import android.content.Context
import android.content.SharedPreferences
import com.citiasia.blanjaloka.core.domain.model.Lokasi
import com.citiasia.blanjaloka.core.domain.model.Pasar
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)
}