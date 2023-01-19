package com.example.nile0117.util.exception

enum class NileCommonError(
    private val code: Int,
    private val message: String,
    private val statusCode: Int = 200
): NileError {
    NOT_FOUND(404, "Not Found", 404),
    INVALID_PARAMETER(400, "Invalid parameter", 400),
    INVALID_TOKEN(9400, "Invalid token", 401),
    ACCESS_TOKEN_EXPIRED(9401, "Access token expired.", 401),
    REFRESH_TOKEN_EXPIRED(9402, "Refresh token expired.", 401),

    INVALID_SLUG(8400, "Invalid slug", 400),
    INVALID_TEXT(8401, "Invalid text for hashtag", 400);

    override fun getErrorCode() = code
    override fun getErrorMessage() = message
    override fun getHttpStatus() = statusCode
}