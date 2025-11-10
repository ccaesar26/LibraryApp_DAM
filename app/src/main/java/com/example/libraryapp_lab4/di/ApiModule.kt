package com.example.libraryapp_lab4.di

import com.example.libraryapp_lab4.remote.BookApiService
import com.example.libraryapp_lab4.remote.BookApiServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModule {

    @Binds
    @Singleton
    abstract fun bindBookApiService(
        bookApiServiceImpl: BookApiServiceImpl
    ): BookApiService
}