package com.example.libraryapp_lab4.data.model

import androidx.compose.ui.graphics.Color

/**
 * Reprezintă modelul de date pentru o carte.
 * Folosim o data class deoarece scopul ei principal este stocarea de date.
 * Compilatorul generează automat equals(), hashCode(), toString() etc.
 */
data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val year: Int,
    val description: String,
    val category: BookCategory
)

/**
 * Un enum "inteligent" care nu doar definește categoriile,
 * dar asociază și o culoare specifică fiecăreia.
 * Această culoare va fi folosită pentru fundalul cardului.
 */
enum class BookCategory(val color: Color) {
    FICTION(Color(0xFFB39DDB)),      // Mov lavandă
    NON_FICTION(Color(0xFF90CAF9)), // Albastru deschis
    SCIENCE(Color(0xFFA5D6A7)),     // Verde pastel
    HISTORY(Color(0xFFFFCC80))      // Portocaliu piersică
}