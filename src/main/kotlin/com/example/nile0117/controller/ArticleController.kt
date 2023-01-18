package com.example.nile0117.controller

import com.example.nile0117.domain.entity.Article
import com.example.nile0117.domain.enums.Status
import com.example.nile0117.repository.ArticleRepository
import com.example.nile0117.service.ArticleService
import com.example.nile0117.util.exception.NileCommonError
import com.example.nile0117.util.exception.NileException
import com.example.nile0117.util.response.NileResponse
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime

@RestController
class ArticleController {

    @Autowired
    lateinit var articleService: ArticleService

    @Autowired
    lateinit var articleRepository: ArticleRepository

    @Autowired
    lateinit var restTemplate: RestTemplate

    companion object: KLogging()

    // create
    @PostMapping("/article")
    fun addArticle(
        @RequestBody payload: Article
    ): ResponseEntity<*> {
        val isSlugExist: Boolean = articleRepository.findBySlug(payload.slug) != null

        // 이미 기존에 존재하는 slug인지
        if (isSlugExist) {
            throw NileException(NileCommonError.INVALID_SLUG)
        }

        // slug 입력 있는지
        if (payload.slug.isNullOrBlank()) {
            throw NileException(NileCommonError.INVALID_PARAMETER)
        }

        val nileArticle = Article(
            payload.slug
        )
        nileArticle.status = payload.status ?: Status.HIDDEN
        nileArticle.createdAt = LocalDateTime.now()
        nileArticle.creator = payload.creator ?: "none"

        articleService.addArticle(nileArticle)

        return ResponseEntity.ok().build<Any>()
    }

    // read
    @GetMapping("/articles")
    fun getArticles(
        @PageableDefault(size = 10) pageable: Pageable
    ): ResponseEntity<*> {
        return ResponseEntity.ok(
            NileResponse(
                result = articleService.getArticles()
            )
        )
    }

    @GetMapping("/article")
    fun getArticle(
        @RequestParam("slug", required = false, defaultValue = "") slug: String?
    ): ResponseEntity<*> {
        if (slug.isNullOrBlank()) {
            throw NileException(NileCommonError.INVALID_PARAMETER)
        }

        val article = slug?.let { articleService.getArticleBySlug(slug!!) }
            ?: throw NileException(NileCommonError.NOT_FOUND)

        return ResponseEntity.ok(
            NileResponse(
                result = article
            )
        )
    }

    // update
    @PutMapping("/article")
    fun updateArticle(
        @RequestParam("slug", required = false, defaultValue = "") slug: String?,
        @RequestBody request: Article
    ): ResponseEntity<*> {
        if (slug.isNullOrBlank()) {
            throw NileException(NileCommonError.INVALID_PARAMETER)
        }

        val targetArticle = slug?.let { articleService.getArticleBySlug(slug) }
            ?: throw NileException(NileCommonError.NOT_FOUND)

        targetArticle.slug = request.slug
        targetArticle.status = request.status
        targetArticle.updatedAt = LocalDateTime.now()
        targetArticle.openedAt = request.openedAt
        targetArticle.creator = request.creator

        articleService.addArticle(targetArticle)

        return ResponseEntity.ok().build<Any>()
    }

    // delete
    @DeleteMapping("/article")
    fun removeArticle(
        @RequestParam("slug", required = false, defaultValue = "") slug: String?
    ): ResponseEntity<*> {
        if (slug.isNullOrBlank()) {
            throw NileException(NileCommonError.INVALID_PARAMETER)
        }

        val nileArticle: Article = articleService.getArticleBySlug(slug)
        articleService.removeArticleBySlug(nileArticle.slug)

        return ResponseEntity.ok(
            NileResponse(
                result = nileArticle
            )
        )
    }
}