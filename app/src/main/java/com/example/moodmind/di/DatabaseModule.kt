package com.example.moodmind.di

import android.content.Context
import com.example.moodmind.data.AppDatabase
import com.example.moodmind.data.MoodDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideMoodMindDao(appDatabase: AppDatabase): MoodDAO {
        return appDatabase.moodDao()
    }

    @Provides
    @Singleton
    fun provideMoodMindAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getDatabase(appContext)
    }
}