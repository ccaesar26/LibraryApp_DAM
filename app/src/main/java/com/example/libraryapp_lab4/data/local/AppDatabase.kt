package com.example.libraryapp_lab4.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.libraryapp_lab4.data.local.entity.AuthorEntity
import com.example.libraryapp_lab4.data.local.entity.BookEntity

@Database(
    entities = [BookEntity::class, AuthorEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}