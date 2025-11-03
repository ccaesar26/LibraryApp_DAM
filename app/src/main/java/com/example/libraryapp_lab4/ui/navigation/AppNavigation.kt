package com.example.libraryapp_lab4.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.libraryapp_lab4.ui.book.BookDetailScreen
import com.example.libraryapp_lab4.ui.book.BookListScreen
import com.example.libraryapp_lab4.ui.home.HomeScreen

/**
Definește graful de navigație al aplicației.
@param navController Controller-ul care gestionează navigația.
@param paddingValues Padding-ul aplicat de Scaffold pentru a nu suprapune conținutul cu barele.
 */
@Composable
fun AppNavigation(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        // Ecranul "Acasă"
        composable(Screen.Home.route) {
            HomeScreen()
        }
        // Ecranul cu lista de cărți
        composable(Screen.BookList.route) {
            BookListScreen(
                onBookClick = { bookId ->
                    // Navigăm la ecranul de detalii, transmițând ID-ul cărții.
                    navController.navigate(Screen.BookDetail.createRoute(bookId))
                }
            )
        }

        // Ecranul de detalii pentru o carte.
        // Definește un argument 'bookId' de tip Int.
        composable(
            route = Screen.BookDetail.route,
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Extragem argumentul din navigație.
            val bookId = backStackEntry.arguments?.getInt("bookId")
            if (bookId != null) {
                BookDetailScreen(bookId = bookId)
            }
        }
    }
}