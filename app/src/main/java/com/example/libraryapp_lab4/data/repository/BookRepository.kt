package com.example.libraryapp_lab4.data.repository

import com.example.libraryapp_lab4.data.model.Book
import com.example.libraryapp_lab4.data.model.BookCategory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository-ul gestionează sursele de date pentru cărți.
 * Momentan, folosește date hardcodate. În viitor, poate interoga o bază de date locală sau un API.
 *
 * @Inject constructor() îi spune lui Hilt cum să creeze o instanță a acestei clase.
 * @Singleton asigură că va exista o singură instanță a acestui repository în întreaga aplicație.
 */
@Singleton
class BookRepository @Inject constructor() {

    private val books = listOf(
        Book(
            id = 1,
            title = "The Great Gatsby",
            author = "F. Scott Fitzgerald",
            year = 1925,
            description = "A story of the fabulously wealthy Jay Gatsby and his love for the beautiful Daisy Buchanan.",
            category = BookCategory.FICTION
        ),
        Book(
            id = 2,
            title = "Sapiens: A Brief History of Humankind",
            author = "Yuval Noah Harari",
            year = 2011,
            description = "A book that explores the history of humankind, from the Stone Age to the 21st century.",
            category = BookCategory.NON_FICTION
        ),
        Book(
            id = 3,
            title = "A Brief History of Time",
            author = "Stephen Hawking",
            year = 1988,
            description = "A landmark volume in science writing by one of the great minds of our time.",
            category = BookCategory.SCIENCE
        ),
        Book(
            id = 4,
            title = "The Guns of August",
            author = "Barbara W. Tuchman",
            year = 1962,
            description = "A book about the first month of World War I. It centers on the prelude to the war and the beginning of the conflict.",
            category = BookCategory.HISTORY
        ),
        Book(
            id = 5,
            title = "Dune",
            author = "Frank Herbert",
            year = 1965,
            description = "Set on the desert planet Arrakis, Dune is the story of the boy Paul Atreides, heir to a noble family tasked with ruling an inhospitable world where the only thing of value is the 'spice' melange.",
            category = BookCategory.SCIENCE
        ),
        Book(
            id = 6,
            title = "1984",
            author = "George Orwell",
            year = 1949,
            description = "A dystopian novel set in Airstrip One, a province of the superstate Oceania in a world of perpetual war, omnipresent government surveillance, and public manipulation.",
            category = BookCategory.FICTION
        ),
        Book(
            id = 7,
            title = "Atomic Habits",
            author = "James Clear",
            year = 2018,
            description = "An easy and proven way to build good habits and break bad ones. It offers a framework for improving every day.",
            category = BookCategory.NON_FICTION
        ),
        Book(
            id = 8,
            title = "The Hobbit",
            author = "J.R.R. Tolkien",
            year = 1937,
            description = "The story of Bilbo Baggins, a hobbit who is whisked away on an unexpected journey by the wizard Gandalf and a company of thirteen dwarves.",
            category = BookCategory.FICTION
        ),
        Book(
            id = 9,
            title = "Cosmos",
            author = "Carl Sagan",
            year = 1980,
            description = "One of the bestselling science books of all time. In clear-eyed prose, Sagan reveals a jewel-like blue world inhabited by a life form that is just beginning to discover its own identity and to venture into the vast ocean of space.",
            category = BookCategory.SCIENCE
        ),
        Book(
            id = 10,
            title = "A People's History of the United States",
            author = "Howard Zinn",
            year = 1980,
            description = "A book that presents American history from the perspective of and through the experiences of the common people.",
            category = BookCategory.HISTORY
        ),
        Book(
            id = 11,
            title = "Thinking, Fast and Slow",
            author = "Daniel Kahneman",
            year = 2011,
            description = "The book's central thesis is a dichotomy between two modes of thought: 'System 1' is fast, instinctive and emotional; 'System 2' is slower, more deliberative, and more logical.",
            category = BookCategory.NON_FICTION
        ),
        Book(
            id = 12,
            title = "To Kill a Mockingbird",
            author = "Harper Lee",
            year = 1960,
            description = "The unforgettable novel of a childhood in a sleepy Southern town and the crisis of conscience that rocked it.",
            category = BookCategory.FICTION
        ),
        Book(
            id = 13,
            title = "The Selfish Gene",
            author = "Richard Dawkins",
            year = 1976,
            description = "A book on evolution that builds upon the principal theory of George C. Williams's Adaptation and Natural Selection.",
            category = BookCategory.SCIENCE
        ),
        Book(
            id = 14,
            title = "Guns, Germs, and Steel",
            author = "Jared Diamond",
            year = 1997,
            description = "An attempt to explain why Eurasian and North African civilizations have survived and conquered others, while arguing against the idea that Eurasian hegemony is due to any form of Eurasian intellectual, moral, or inherent genetic superiority.",
            category = BookCategory.HISTORY
        )
    )

    /**
     * Returnează lista completă de cărți.
     */
    suspend fun getAllBooks(): List<Book> {
        return books
    }

    /**
     * Găsește și returnează o singură carte pe baza ID-ului.
     * Returnează null dacă nu se găsește cartea.
     */
    fun getBookById(id: Int): Book? {
        return books.find { it.id == id }
    }
}