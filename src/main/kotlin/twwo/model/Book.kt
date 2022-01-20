package twwo.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

object Books : Table() {
    val bookId = integer("book_id").autoIncrement()
    val title = varchar("title", 255)
    val author = varchar("author", length = 255)
    override val primaryKey = PrimaryKey(bookId)
}

@Serializable
data class Book(
    val bookId: Int,
    val title: String,
    val author: String
)
