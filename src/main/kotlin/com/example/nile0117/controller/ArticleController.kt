package com.example.nile0117.controller

import com.example.nile0117.domain.entity.Article
import com.example.nile0117.domain.entity.ArticleContent
import com.example.nile0117.repository.ArticleContentRepository
import com.example.nile0117.repository.ArticleRepository
import com.example.nile0117.service.ArticleService
import com.example.nile0117.util.exception.NileCommonError
import com.example.nile0117.util.exception.NileException
import com.example.nile0117.util.response.NileResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class ArticleController {

    @Autowired
    lateinit var articleService: ArticleService

    @Autowired
    lateinit var articleRepository: ArticleRepository

    @Autowired
    lateinit var articleContentRepository: ArticleContentRepository

    // create
    @PostMapping("/article")
    fun addArticle(
        @RequestBody payload: Article
    ): ResponseEntity<*> {
        // slug 입력 여부 확인
        // 유효성 체크
        if (payload.slug.isBlank()) {
            throw NileException(NileCommonError.INVALID_PARAMETER)
        }

        val targetArticle = Article(
            payload.slug,
            payload.status,
            payload.openedAt,
            payload.nftCreator ?: "unknown"
        )
        articleService.addArticle(targetArticle)

        payload.contents.forEach {
            articleContentRepository.save(ArticleContent(it.language, it.title, it.description, it.content, targetArticle.id))
        }

        return ResponseEntity.ok().build<Any>() // response에서 데이터 확인 가능하도록
    }

    // read
    @GetMapping("/articles")
    fun getArticles(): ResponseEntity<*> {
        val targetArticles =  articleService.getArticles()

        targetArticles.forEach {
            it.contents = mutableListOf()
            it.contents.addAll(articleContentRepository.findAllByArticleId(it.id))
        }

        return ResponseEntity.ok(
            NileResponse(
                result = targetArticles
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

        val targetArticle = articleService.getArticleBySlug(slug)
        val targetArticleContents = articleContentRepository.findAllByArticleId(targetArticle.id)

        targetArticle.contents = mutableListOf()
        targetArticle.contents.addAll(targetArticleContents)

        return ResponseEntity.ok(
            NileResponse(
                result = targetArticle
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

        val targetArticle = articleService.getArticleBySlug(slug)

        targetArticle.slug = request.slug
        targetArticle.status = request.status
        targetArticle.openedAt = request.openedAt // openedAt 지정하지 않으면, 현재 시점으로 기본 설정
        targetArticle.nftCreator = request.nftCreator

        articleService.addArticle(targetArticle)

        // content update
        val prevContents = articleContentRepository.findAllByArticleId(targetArticle.id)
        prevContents.forEach {
            articleContentRepository.delete(it) // 삭제 후 재등록 말고 수정으로!
            // postgres는 update 시 기존 거를 지우고 다시 만듦
            // update 관련 글 찾아보기
        }
        targetArticle.contents.forEach {
            articleContentRepository.save(ArticleContent(it.language, it.title, it.description, it.content, targetArticle.id))
        }

        return ResponseEntity.ok().build<Any>()
    }

    // delete
    // 조회를 제외하고는 권한 처리
    @DeleteMapping("/article")
    fun removeArticle(
        @RequestParam("slug", required = false, defaultValue = "") slug: String?
    ): ResponseEntity<*> {
        if (slug.isNullOrBlank()) {
            throw NileException(NileCommonError.INVALID_PARAMETER)
        }

        val targetArticle: Article = articleService.getArticleBySlug(slug)
        articleService.removeArticleBySlug(targetArticle.slug)

        val targetArticleContents = articleContentRepository.findAllByArticleId(targetArticle.id)
        targetArticleContents.forEach {
            articleContentRepository.delete(it)
        }

        return ResponseEntity.ok().build<Any>()
    }
}