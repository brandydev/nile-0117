package com.example.nile0117.service

import com.example.nile0117.domain.entity.Article
import com.example.nile0117.domain.entity.ArticleContent
import com.example.nile0117.domain.enums.Status
import com.example.nile0117.repository.ArticleContentRepository
import com.example.nile0117.repository.ArticleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ArticleService {

    @Autowired
    lateinit var articleRepository: ArticleRepository

    @Autowired
    lateinit var articleContentRepository: ArticleContentRepository

    // duplicate check
    fun isUnique(slug: String): Boolean {
        return !articleRepository.existsBySlug(slug)
    }

    // create
    fun addArticle(request: Article): Article? {
        if (isUnique(request.slug)) {
            val targetArticle = Article(
                request.slug,
                request.status ?: Status.HIDDEN,
                request.openedAt ?: LocalDateTime.now(),
                request.nftCreator ?: "unknown"
            )
            articleRepository.save(targetArticle)

            request.contents.forEach {
                articleContentRepository.save(ArticleContent(targetArticle.id, it.language, it.title, it.content))
            }

            return targetArticle
        } else {
            return null
        }
    }

    // read
    fun getArticles(): List<Article> {
        val targetArticles = articleRepository.findAllByIdIsNotNull()
        targetArticles.forEach {
            it.contents = mutableListOf()
            it.contents.addAll(articleContentRepository.findAllByArticleId(it.id))
        }

        return targetArticles
    }

    fun getArticleBySlug(slug: String): Article? {
        val isExist = articleRepository.existsBySlug(slug)
        return if (!isExist) {
            null
        } else {
            articleRepository.findBySlug(slug)
        }
    }

    // update
    fun updateArticleBySlug(slug: String, request: Article): Article? {
        val isExist = articleRepository.existsBySlug(slug)
        return if (!isExist) {
            null
        } else {
            // todo: 로직 검토 및 수정
            removeArticleBySlug(slug)
            addArticle(request)
        }
    }

    // delete
    fun removeArticleBySlug(slug: String): Article? {
        val isExist = articleRepository.existsBySlug(slug)
        return if (!isExist) {
            null
        } else {
            val targetArticle = articleRepository.findBySlug(slug)
            articleRepository.deleteById(targetArticle!!.id!!)
            val targetArticleContents = articleContentRepository.findAllByArticleId(targetArticle.id)
            targetArticleContents.forEach {
                articleContentRepository.delete(it)
            }
            targetArticle
        }
    }
}