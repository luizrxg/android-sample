package com.wishes.di

import android.content.Context
import com.wishes.database.dao.*
import com.wishes.database.db.AppDatabase
import com.wishes.database.db.DatabaseTransactionRunner
import com.wishes.database.db.RoomTransactionRunner
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
    fun provideWishDao(appDatabase: AppDatabase): WishDao {
        return appDatabase.wishDao()
    }

    @Provides
    fun provideLinkDao(appDatabase: AppDatabase): LinkDao {
        return appDatabase.linkDao()
    }

    @Provides
    fun provideSaldoDao(appDatabase: AppDatabase): SaldoDao {
        return appDatabase.saldoDao()
    }

    @Provides
    fun provideSalarioDao(appDatabase: AppDatabase): SalarioDao {
        return appDatabase.salarioDao()
    }

    @Provides
    fun provideSalarioDepositadoDao(appDatabase: AppDatabase): SalarioDepositadoDao {
        return appDatabase.salarioDepositadoDao()
    }
}


@InstallIn(SingletonComponent::class)
@Module
abstract class DatabaseModuleBinds {

    @Singleton
    @Binds
    abstract fun provideDatabaseTransactionRunner(runner: RoomTransactionRunner): DatabaseTransactionRunner
}