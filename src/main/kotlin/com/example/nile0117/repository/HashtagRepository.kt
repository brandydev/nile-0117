package com.example.nile0117.repository

import com.example.nile0117.domain.entity.Hashtag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface HashtagRepository: JpaRepository<Hashtag, UUID> {
    fun findAllByIdIsNotNull(): List<Hashtag>
    fun findAllByIdIsNotNull(pageable: Pageable): Page<Hashtag>
    fun findByText(text: String): Hashtag?
}