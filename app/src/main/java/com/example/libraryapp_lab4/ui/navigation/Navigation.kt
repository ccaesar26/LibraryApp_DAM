package com.example.libraryapp_lab4.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Sealed class pentru a defini rutele într-un mod sigur (type-safe).
 * Evităm folosirea de string-uri simple pentru a preveni erorile de tastare.
 */
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Rounded.Home)
    object BookList : Screen("book_list", "Browse", Icons.AutoMirrored.Rounded.List)
    object BookDetail : Screen("book_detail/{bookId}", "Details", Icons.AutoMirrored.Rounded.List) {
        fun createRoute(bookId: Int) = "book_detail/$bookId"
    }
}