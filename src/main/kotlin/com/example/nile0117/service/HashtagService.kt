package com.example.nile0117.service

import com.example.nile0117.domain.entity.Hashtag
import com.example.nile0117.repository.HashtagRepository
import com.example.nile0117.util.exception.NileCommonError
import com.example.nile0117.util.exception.NileException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HashtagService {

    @Autowired
    lateinit var hashtagRepository: HashtagRepository

    // create
    fun addHashtag(hashtag: Hashtag) = hashtagRepository.save(hashtag)

    // read
    fun getHashtags() = hashtagRepository.findAllByIdIsNotNull()
    fun getArticleByText(text: String): Hashtag {
        return hashtagRepository.findByText(text) ?: throw NileException(NileCommonError.NOT_FOUND)
    }

    // delete
    fun removeHashtagByText(text: String) {
        val nileHashtag = text?.let { hashtagRepository.findByText(text) }
            ?: throw NileException(NileCommonError.NOT_FOUND)
        hashtagRepository.delete(nileHashtag)
    }
}