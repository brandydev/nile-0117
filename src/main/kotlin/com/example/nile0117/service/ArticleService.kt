package com.example.nile0117.service

import com.example.nile0117.domain.entity.Article
import com.example.nile0117.repository.ArticleContentRepository
import com.example.nile0117.repository.ArticleRepository
import com.example.nile0117.util.NileCommonError
import com.example.nile0117.util.NileException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ArticleService {

    @Autowired
    lateinit var articleRepository: ArticleRepository

    @Autowired
    lateinit var articleContentRepository: ArticleContentRepository

    // create
    fun addArticle(article: Article) = articleRepository.save(article)

    // read
    fun getArticles() = articleRepository.findAllByIdIsNotNullOrderByOpenedAtDesc()
    fun getArticleBySlug(slug: String): Article {
        return articleRepository.findBySlug(slug) ?: throw NileException(NileCommonError.NOT_FOUND)
    }

    // update
    fun editArticle(article: Article) = articleRepository.save(article)

    // delete
    fun removeArticle(id: UUID) = articleRepository.deleteById(id)

}