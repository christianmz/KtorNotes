package com.meazza.route

import com.meazza.data.checkPasswordForEmail
import com.meazza.data.request.AccountRequest
import com.meazza.data.response.SimpleResponse
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.features.ContentTransformationException
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.loginRoute() {
    route("/login") {
        post {

            val request = try {
                call.receive<AccountRequest>()
            } catch (e: ContentTransformationException) {
                call.respond(BadRequest)
                return@post
            }

            val isPasswordCorrect = checkPasswordForEmail(request.email, request.password)

            if (isPasswordCorrect) {
                call.respond(OK, SimpleResponse(true, "Your are now logged in!"))
            } else {
                call.respond(OK, SimpleResponse(false, "The E-Mail or password is incorrect"))
            }
        }
    }
}