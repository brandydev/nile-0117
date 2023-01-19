package com.example.nile0117.service

import com.example.nile0117.domain.entity.Article
import com.example.nile0117.domain.entity.ArticleContent
import com.example.nile0117.repository.ArticleContentRepository
import com.example.nile0117.repository.ArticleRepository
import com.example.nile0117.util.exception.NileCommonError
import com.example.nile0117.util.exception.NileException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ArticleService {

    @Autowired
    lateinit var articleRepository: ArticleRepository

    @Autowired
    lateinit var articleContentRepository: ArticleContentRepository

    // create
    fun addArticle(article: Article) = articleRepository.save(article)
    fun addArticleContent(articleContent: ArticleContent) = articleContentRepository.save(articleContent)

    // read
    fun getArticles() = articleRepository.findAllByIdIsNotNullOrderByOpenedAtDesc()
    fun getArticleBySlug(slug: String): Article {
        return articleRepository.findBySlug(slug) ?: throw NileException(NileCommonError.NOT_FOUND)
    }

    // update

    // delete
    fun removeArticleBySlug(slug: String) {
        val nileArticle = articleRepository.findBySlug(slug)
        nileArticle?.let { articleRepository.delete(it) }
            ?: throw NileException(NileCommonError.NOT_FOUND)
    }

}