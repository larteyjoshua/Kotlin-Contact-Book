package joshcode.plugins

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.swagger.codegen.v3.generators.html.StaticHtmlCodegen
import joshcode.dto.DAOFacadeImpl


import joshcode.models.Contact
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.json.Json

fun Application.configureRouting() {
    val contactService = DAOFacadeImpl()

    routing {

        get("/") {
            call.respondText("Hello World!")
        }

        route("/v1/contacts") {

            post("/add") {
                val contact = call.receive<Contact>()
                println(contact.name)
                val addedContact: Contact? = contactService.addNewContact(contact.name, contact.email, contact.telephone, contact.gpslocation, contact.town)
                println(addedContact)
               call.respond(hashMapOf("contact" to addedContact))
            }

            get ("/all") {
                call.respond(contactService.allContacts())
            }

            put("/edit"){
                val contact = call.receive<Contact>()
               val isEdited =   contactService.editContact(contact.id, contact.name, contact.email, contact.telephone, contact.gpslocation, contact.town)
                if (isEdited){
                    val editedContact = contactService.contact(contact.id)
                    call.respond(hashMapOf("contact" to editedContact))
                }
            }

            delete("/delete/{id}") {
                val id = call.parameters["id"]
                var deletedContact: Contact? = Contact(0, "","", "","", "")
                var isDeleted: Boolean = false
                if(id != null){
                    deletedContact = contactService.contact(id.toInt())
                    isDeleted = contactService.deleteContact(id.toInt())
                }
                if(isDeleted){
                    call.respond(hashMapOf("contact" to deletedContact))
                }
            }

            get ("/{id}") {
                val id = call.parameters["id"]
                if(id != null) {
                     val singleContact: Contact? = contactService.contact(id.toInt())
                    call.respond(hashMapOf("contact" to singleContact))
                }
            }


        }
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
        openAPI(path="openapi", swaggerFile = "openapi/documentation.yaml") {
            codegen = StaticHtmlCodegen()
        }
    }
}
