package com.example.libraryapp_lab4

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Clasa Application a aplicației.
 * Adnotarea @HiltAndroidApp este esențială pentru ca Hilt să poată genera codul necesar
 * pentru injecția de dependențe.
 */
@HiltAndroidApp
class LibraryApplication : Application()