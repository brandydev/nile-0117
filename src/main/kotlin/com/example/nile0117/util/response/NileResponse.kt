package com.example.nile0117.util.response

data class NileResponse(
    val errorCode: Int? = 0, // 우리 로직에서 정의한 에러 코드
    val status: Int? = 200, // http status
    val message: String? = "OK",
    val result: Any? = null
)
