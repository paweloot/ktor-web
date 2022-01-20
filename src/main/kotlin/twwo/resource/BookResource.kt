package twwo.resource

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import twwo.service.BookService

fun Route.book(bookService: BookService) {

    route("/books") {
        get {
            call.respond(bookService.getAllBooks())
        }
    }

    route("/book") {

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: throw IllegalStateException("No book id provided")
            bookService.getBook(id)
                ?.let { book -> call.respond(book) }
                ?: call.respond(HttpStatusCode.NotFound)

        }

    }
}