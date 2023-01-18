package com.example.nile0117.domain.entity

import com.example.nile0117.domain.base.BaseEntity
import com.example.nile0117.domain.enums.Language
import jakarta.persistence.*
import java.util.*

@Entity
data class ArticleContent(
    @Enumerated(EnumType.STRING)
    var language: Language? = null,
    var title: String? = null,
    var description: String? = null,
    var content: String? = null,
): BaseEntity() {
    var articleId: UUID? = null
}