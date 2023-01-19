package com.example.nile0117.repository

import com.example.nile0117.domain.entity.Article
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ArticleRepository: JpaRepository<Article, UUID> {
    fun existsBySlug(slug: String): Boolean

    fun findAllByIdIsNotNull(): List<Article>
    fun findAllByIdIsNotNull(pageable: Pageable): Page<Article>
    fun findAllByIdIsNotNullOrderByOpenedAtDesc(): List<Article>
    fun findAllByIdIsNotNullOrderByOpenedAtDesc(pageable: Pageable): Page<Article>
    fun findBySlug(slug: String): Article?

    fun deleteBySlug(slug: String)
}