package com.example.libraryapp_lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.libraryapp_lab4.ui.navigation.AppNavigation
import com.example.libraryapp_lab4.ui.navigation.Screen
import com.example.libraryapp_lab4.ui.theme.LibraryApp_Lab4Theme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activitatea principală a aplicației.
 *
 * @AndroidEntryPoint este necesară pentru ca Hilt să poată injecta dependențe
 * în clasele Android Framework precum Activity.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibraryApp_Lab4Theme {
                MainApp()
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    // NavController este piesa centrală pentru navigație.
    // `rememberNavController` îl creează și îl reține peste recompoziții.
    val navController = rememberNavController()
    val navItems = listOf(Screen.Home, Screen.BookList)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                navItems.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // PopUp la destinația de start pentru a evita acumularea de ecrane
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Evită crearea unei noi instanțe dacă ecranul este deja în stivă
                                launchSingleTop = true
                                // Restaurează starea la re-selectare
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        // Pasează navController-ul și padding-ul la graful de navigație.
        AppNavigation(navController = navController, paddingValues = innerPadding)
    }
}