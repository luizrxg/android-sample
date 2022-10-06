package com.defaultapp.di

import android.content.Context
import com.defaultapp.database.dao.DefaultDao
import com.defaultapp.database.db.AppDatabase
import com.defaultapp.database.db.DatabaseTransactionRunner
import com.defaultapp.database.db.RoomTransactionRunner
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideDefaultDao(appDatabase: AppDatabase): DefaultDao {
        return appDatabase.defaultDao()
    }
}


@InstallIn(SingletonComponent::class)
@Module
abstract class DatabaseModuleBinds {

    @Singleton
    @Binds
    abstract fun provideDatabaseTransactionRunner(runner: RoomTransactionRunner): DatabaseTransactionRunner
}