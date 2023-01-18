package com.example.nile0117.util.exception

interface NileError {
    fun getErrorCode(): Int
    fun getErrorMessage(): String?
}