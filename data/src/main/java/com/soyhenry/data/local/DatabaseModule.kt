package com.soyhenry.data.local

import android.content.Context
import androidx.room.Room
import com.soyhenry.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "soypeya-database")
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    fun provideCartItemDao(database: AppDatabase): CartItemDao {
        return database.cartItemDao()
    }

    @Provides
    fun provideOrderHistoryDao(database: AppDatabase): OrderDao {
        return database.orderHistoryDao()
    }

    @Provides
    fun provideOrderItemDao(database: AppDatabase): OrderItemDao {
        return database.orderItemDao()
    }
}