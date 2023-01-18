package com.example.nile0117.domain.dto

import jakarta.validation.constraints.NotBlank

data class HashtagRequest(
    @field:NotBlank
    val text: String
)