package com.wishes.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wishes.database.entity.*
import com.wishes.util.DATABASE_NAME
import com.wishes.database.converter.AppTypeConverters
import com.wishes.database.dao.*

@Database(
    entities = [
        WishEntity::class,
        LinkEntity::class,
        SaldoEntity::class,
        SalarioEntity::class,
        SalarioDepositadoEntity::class,
    ],
    version = 1, exportSchema = false
)
@TypeConverters(AppTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wishDao(): WishDao
    abstract fun linkDao(): LinkDao
    abstract fun saldoDao(): SaldoDao
    abstract fun salarioDao(): SalarioDao
    abstract fun salarioDepositadoDao(): SalarioDepositadoDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}