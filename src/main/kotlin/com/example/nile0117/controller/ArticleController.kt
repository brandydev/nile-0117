package com.example.nile0117.controller

import com.example.nile0117.domain.entity.Article
import com.example.nile0117.domain.entity.ArticleContent
import com.example.nile0117.domain.enums.Status
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
        if (payload.slug.isBlank()) {
            return ResponseEntity.ok(
                NileResponse(
                    errorCode = NileCommonError.INVALID_PARAMETER.getErrorCode(),
                    status = NileCommonError.INVALID_PARAMETER.getHttpStatus(),
                    message = "article 생성을 위해서는 slug 입력이 필요합니다."
                )
            )
        }

        val createdArticle: Article? = articleService.addArticle(payload)
        createdArticle?.let { return ResponseEntity.ok(
            NileResponse(
                message = "article이 성공적으로 생성되었습니다.",
                result = createdArticle
            )
        ) } ?: return ResponseEntity.ok(
            NileResponse(
                errorCode = NileCommonError.INVALID_SLUG.getErrorCode(),
                status = NileCommonError.INVALID_SLUG.getHttpStatus(),
                message = "이미 존재하는 slug입니다."
            )
        )
    }

    // read
    @GetMapping("/articles")
    fun getArticles(): ResponseEntity<*> {
        val selectedArticles =  articleService.getArticles()

        return ResponseEntity.ok(
            NileResponse(
                message = "전체 article 조회에 성공했습니다.",
                result = selectedArticles
            )
        )
    }

    @GetMapping("/article")
    fun getArticle(
        @RequestParam("slug", required = false, defaultValue = "") slug: String?
    ): ResponseEntity<*> {
        if (slug.isNullOrBlank()) {
            return ResponseEntity.ok(
                NileResponse(
                    errorCode = NileCommonError.INVALID_PARAMETER.getErrorCode(),
                    status = NileCommonError.INVALID_PARAMETER.getHttpStatus(),
                    message = "article 조회를 위해서는 slug 입력이 필요합니다."
                )
            )
        }

        val selectedArticle: Article? = articleService.getArticleBySlug(slug)
        selectedArticle?.let { return ResponseEntity.ok(
            NileResponse(
                message = "article이 성공적으로 조회되었습니다.",
                result = selectedArticle
            )
        ) } ?: return ResponseEntity.ok(
            NileResponse(
                errorCode = NileCommonError.INVALID_SLUG.getErrorCode(),
                status = NileCommonError.INVALID_SLUG.getHttpStatus(),
                message = "존재하지 않는 article입니다."
            )
        )
    }

    /*
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
     */

    // delete
    @DeleteMapping("/article")
    fun removeArticle(
        @RequestParam("slug", required = false, defaultValue = "") slug: String?
    ): ResponseEntity<*> {
        if (slug.isNullOrBlank()) {
            return ResponseEntity.ok(
                NileResponse(
                    errorCode = NileCommonError.NOT_FOUND.getErrorCode(),
                    status = NileCommonError.NOT_FOUND.getHttpStatus(),
                    message = "article 삭제를 위해서는 slug 입력이 필요합니다."
                )
            )
        }

        val deletedArticle: Article? = articleService.removeArticleBySlug(slug)
        deletedArticle?.let { return ResponseEntity.ok(
            NileResponse(
                message = "article이 성공적으로 삭제되었습니다.",
                result = deletedArticle
            )
        ) } ?: return ResponseEntity.ok(
            NileResponse(
                errorCode = NileCommonError.INVALID_SLUG.getErrorCode(),
                status = NileCommonError.INVALID_SLUG.getHttpStatus(),
                message = "존재하지 않는 slug입니다."
            )
        )
    }
}