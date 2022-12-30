package joshcode.dto
import joshcode.models.*

interface DAOFacade {
    suspend fun allContacts(): List<Contact>
    suspend fun contact(id: Int): Contact?
    suspend fun addNewContact(name: String, email: String, telephone: String, gpslocation: String, town: String): Contact?
    suspend fun editContact(id: Int,name: String, email: String, telephone: String, gpslocation: String, town: String): Boolean
    suspend fun deleteContact(id: Int): Boolean
}