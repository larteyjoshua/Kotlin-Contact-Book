package joshcode.dto

import joshcode.models.*
import joshcode.dto.DatabaseFactory.dbQuery
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeImpl: DAOFacade {

    private fun resultRowToContact(row: ResultRow) = Contact(
        id = row[Contacts.id],
        name = row[Contacts.name],
        email = row[Contacts.email],
        telephone = row[Contacts.telephone],
        gpslocation = row[Contacts.gpslocation],
        town = row[Contacts.town]
    )

    override suspend fun allContacts(): List<Contact> = dbQuery {
        Contacts.selectAll().map(::resultRowToContact)
    }

    override suspend fun contact(id: Int): Contact? = dbQuery {
        Contacts
            .select { Contacts.id eq id }
            .map(::resultRowToContact)
            .singleOrNull()
    }

    override suspend fun addNewContact(
        name: String,
        email: String,
        telephone: String,
        gpslocation: String,
        town: String
    ):
            Contact? = dbQuery {
        val insertStatement = Contacts.insert {
            it[Contacts.name] = name
            it[Contacts.email] = email
            it[Contacts.telephone] = telephone
            it[Contacts.gpslocation] = gpslocation
            it[Contacts.town] = town
        }
        println(name)
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToContact)

    }

        override suspend fun editContact(
            id: Int,
            name: String,
            email: String,
            telephone: String,
            gpslocation: String,
            town: String
        ): Boolean = dbQuery {
            Contacts.update({ Contacts.id eq id }) {
                it[Contacts.name] = name
                it[Contacts.email] = email
                it[Contacts.telephone] = telephone
                it[Contacts.gpslocation] = gpslocation
                it[Contacts.town] = town
            } > 0
        }

        override suspend fun deleteContact(id: Int): Boolean = dbQuery {
            Contacts.deleteWhere { Contacts.id eq id } > 0
        }
    }

//    val dao: DAOFacade = DAOFacadeImpl().apply {
//        runBlocking {
//            if (allContacts().isEmpty()) {
//                addNewContact(
//                    name="Joshua Lartey",
//                    email="larteyjoshua@gmail.com",
//                    telephone = "0249643365",
//                    gpsLocation = "GN-1001-1783",
//                    town = "Afienya")
//            }
//        }
//    }
