package joshcode.models
import org.jetbrains.exposed.sql.*
import kotlinx.serialization.*

@Serializable
data class Contact(val id: Int,
                   val name: String,
                   val email: String,
                   val telephone: String,
                   val gpslocation: String,
                   val town: String)

object Contacts : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val email = varchar("email", 128)
    val telephone = varchar("telephone", 128)
    val gpslocation = varchar("gpslocation", 128)
    val town = varchar("town", 128)

    override val primaryKey = PrimaryKey(id)
}