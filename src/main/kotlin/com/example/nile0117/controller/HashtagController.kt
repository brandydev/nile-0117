package com.example.nile0117.controller

import com.example.nile0117.domain.entity.Hashtag
import com.example.nile0117.repository.HashtagRepository
import com.example.nile0117.service.HashtagService
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
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class HashtagController {

    @Autowired
    lateinit var hashtagService: HashtagService

    @Autowired
    lateinit var hashtagRepository: HashtagRepository

    @Autowired
    lateinit var restTemplate: RestTemplate

    companion object: KLogging()

    // create
    @PostMapping("/hashtag")
    fun addHashtag(
        @RequestBody payload: Hashtag
    ): ResponseEntity<*> {
        val isHashtagExist: Boolean = hashtagRepository.findByText(payload.text) != null

        if (isHashtagExist) {
            throw NileException(NileCommonError.INVALID_TEXT)
        }

        if (payload.text.isNullOrBlank()) {
            throw NileException(NileCommonError.INVALID_PARAMETER)
        }

        val nileHashtag = Hashtag(
            payload.text
        )

        hashtagService.addHashtag(nileHashtag)

        return ResponseEntity.ok().build<Any>()
    }

    // read
    @GetMapping("/hashtags")
    fun getHashtags(): ResponseEntity<*> {
        return ResponseEntity.ok(
            NileResponse(
                result = hashtagService.getHashtags()
            )
        )
    }

    @GetMapping("/hashtags/page")
    fun getHashtagsPage(
        @PageableDefault(size = 10) pageable: Pageable
    ): ResponseEntity<*> {
        return ResponseEntity.ok(
            NileResponse(
                result = hashtagService.getHashtagsPage(pageable)
            )
        )
    }

    @GetMapping("/hashtag")
    fun getHashtag(
        @RequestParam("text", required = false, defaultValue = "") text: String?
    ): ResponseEntity<*> {
        if (text.isNullOrBlank()) {
            throw NileException(NileCommonError.INVALID_PARAMETER)
        }

        val hashtag = text?.let { hashtagService.getArticleByText(text) }
            ?: throw NileException(NileCommonError.NOT_FOUND)

        return ResponseEntity.ok(
            NileResponse(
                result = hashtag
            )
        )
    }

    // delete
    @DeleteMapping("/hashtag")
    fun removeHashtag(
        @RequestParam("text", required = false, defaultValue = "") text: String?
    ): ResponseEntity<*> {
        if (text.isNullOrBlank()) {
            throw NileException(NileCommonError.INVALID_PARAMETER)
        }

        val nileHashtag: Hashtag = hashtagService.getArticleByText(text)
        hashtagService.removeHashtagByText(nileHashtag.text)

        return ResponseEntity.ok(
            NileResponse(
                result = nileHashtag
            )
        )
    }
}