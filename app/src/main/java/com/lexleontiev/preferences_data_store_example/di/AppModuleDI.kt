package com.lexleontiev.preferences_data_store_example.di

import android.content.Context
import android.content.SharedPreferences
import com.lexleontiev.preferences_data_store_example.data.UserPreferencesRepo
import com.lexleontiev.preferences_data_store_example.data.UserPreferencesRepo.Companion.PREFS_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModuleDI {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesRepo(sharedPreferences: SharedPreferences): UserPreferencesRepo {
        return UserPreferencesRepo(sharedPreferences)
    }
}