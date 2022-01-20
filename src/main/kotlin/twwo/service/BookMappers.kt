package twwo.service

import org.jetbrains.exposed.sql.ResultRow
import twwo.model.Book
import twwo.model.Books

fun ResultRow.toBook() = Book(
    bookId = get(Books.bookId),
    title = get(Books.title),
    author = get(Books.author)
)