package com.example.nile0117.util.response

data class NileResponse(
    val errorCode: Int? = 0,
    val status: Int? = 200,
    val result: Any? = null,
)
