package com.example.libraryapp_lab4.di

import android.content.Context
import androidx.room.Room
import com.example.libraryapp_lab4.data.local.AppDatabase
import com.example.libraryapp_lab4.data.local.BookDao
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
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "library_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookDao(appDatabase: AppDatabase): BookDao {
        return appDatabase.bookDao()
    }
}