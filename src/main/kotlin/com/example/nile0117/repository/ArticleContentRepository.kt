package com.example.nile0117.repository

import com.example.nile0117.domain.entity.ArticleContent
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ArticleContentRepository: JpaRepository<ArticleContent, UUID> {
    fun findAllByArticleId(articleId: UUID?): List<ArticleContent>
}