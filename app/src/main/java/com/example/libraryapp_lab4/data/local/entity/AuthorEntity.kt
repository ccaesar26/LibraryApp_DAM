package com.example.libraryapp_lab4.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "authors",
    indices = [Index(value = ["name"], unique = true)]
)
data class AuthorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Default value is needed for auto-generation
    val name: String
)
