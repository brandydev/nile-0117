package com.example.nile0117.util.response

data class NilePageInfo(
    val number: Int,
    val size: Int,
    val total: Long,
    val hasNextPage: Boolean,
    val hasPrevPage: Boolean
)
