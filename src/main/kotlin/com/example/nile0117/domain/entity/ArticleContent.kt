package com.example.nile0117.domain.entity

import com.example.nile0117.domain.base.BaseEntity
import com.example.nile0117.domain.enums.Language
import jakarta.persistence.*
import java.util.*

@Entity
data class ArticleContent(
    @Enumerated(EnumType.STRING)
    var language: Language?,
    var title: String?,
    var description: String?,
    var content: String?,
    var articleId: UUID?
): BaseEntity() {
}