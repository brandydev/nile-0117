package com.example.nile0117.util.response

data class NileResponse(
    val errorCode: Int? = 0,
    val status: Int? = 200,
    val message: String? = "",
    val result: Any? = null,
    val results: Collection<Any>? = null,
    val pageInfo: NilePageInfo? = null
)
