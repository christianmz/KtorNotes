package com.meazza.route

import com.meazza.data.checkIfUserExists
import com.meazza.data.collection.User
import com.meazza.data.registerUser
import com.meazza.data.request.AccountRequest
import com.meazza.data.response.SimpleResponse
import com.meazza.data.security.getHashWithSalt
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.features.ContentTransformationException
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.registerRoute() {
    route("/register") {
        post {

            val request = try {
                call.receive<AccountRequest>()
            } catch (e: ContentTransformationException) {
                call.respond(BadRequest)
                return@post
            }

            val userExists = checkIfUserExists(request.email)

            if (!userExists) {
                if (registerUser(User(request.email, getHashWithSalt(request.password)))) {
                    call.respond(OK, SimpleResponse(true, "Successfully created account!"))
                } else {
                    call.respond(OK, SimpleResponse(false, "An unknown error occurred"))
                }
            } else {
                call.respond(OK, SimpleResponse(false, "A user with that E-Mail already exists"))
            }
        }
    }
}