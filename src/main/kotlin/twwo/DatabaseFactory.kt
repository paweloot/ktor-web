package twwo

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import twwo.model.Books

object DatabaseFactory {

    fun init() {
        connect()
        runBlocking {
            createTables()
        }
    }

    private fun connect() {
        val pool = hikari()
        Database.connect(pool)
    }

    private suspend fun createTables() = dbTransaction {
        SchemaUtils.create(Books)

        Books.insert {
            it[title] = "Rok 1984"
            it[author] = "George Orwell"
        }

        Books.insert {
            it[title] = "Nomadland"
            it[author] = "Jessica Bruder"
        }

    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = "jdbc:postgresql://db:5432/vagrant"
            username = "vagrant"
            password = "vagrant"
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        return HikariDataSource(config)
    }


}

suspend fun <T> dbTransaction(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }
