package com.example.libraryapp_lab4.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "books",
    foreignKeys = [
        ForeignKey(
            entity = AuthorEntity::class,
            parentColumns = ["id"], // Connects to the Author's ID
            childColumns = ["authorId"], // From the Book's authorId field
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class BookEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val year: Int,
    val description: String,
    val category: String,
    val authorId: Int
)
