package twwo.plugins

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import twwo.resource.book
import twwo.service.BookService

fun Application.configureRouting() {
    routing {

        val bookService = BookService()
        book(bookService)

        get("/") {
            val bookColSize = bookService.getAllBooks().size
            call.respondText("Total number of books in the database: ${bookColSize.toString()}")
        }

    }
}
