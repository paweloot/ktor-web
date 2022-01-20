package twwo.service

import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import twwo.dbTransaction
import twwo.model.Book
import twwo.model.Books

class BookService {

    suspend fun getAllBooks(): List<Book> = dbTransaction {
        Books.selectAll()
            .map { it.toBook() }
    }

    suspend fun getBook(bookId: Int): Book? = dbTransaction {
        Books.select { Books.bookId eq bookId }
            .map { it.toBook() }
            .singleOrNull()
    }
}