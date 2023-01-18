package com.example.nile0117.repository

import com.example.nile0117.domain.entity.ArticleContent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ArticleContentRepository: JpaRepository<ArticleContent, UUID> {
}