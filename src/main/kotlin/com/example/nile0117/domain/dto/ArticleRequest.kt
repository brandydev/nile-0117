package com.example.nile0117.domain.dto

import com.example.nile0117.domain.enums.Status
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class ArticleRequest(
    @field:NotBlank
    val slug: String,
    val status: Status,
    val openedAt: LocalDateTime,
    val creator: String
)