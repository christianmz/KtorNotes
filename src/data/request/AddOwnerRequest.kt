package com.meazza.data.request

data class AddOwnerRequest(
    val noteId: String,
    val owner: String
)
