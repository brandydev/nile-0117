package com.example.nile0117.domain.dto

import com.example.nile0117.domain.enums.Language
import jakarta.validation.constraints.NotBlank
data class ArticleContentRequest(
    @field:NotBlank
    val language: Language,
    @field:NotBlank
    val title: String,
    @field:NotBlank
    val description: String,
    @field:NotBlank
    val content: String
)