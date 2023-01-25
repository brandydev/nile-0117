package com.example.nile0117.domain.entity

import com.example.nile0117.domain.base.BaseEntity
import com.example.nile0117.domain.enums.Status
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.LocalDateTime
import kotlin.jvm.Transient

@Entity
data class Article(
    var slug: String, // unique index 처리 >> db 차원에서 처리 가능
    @Enumerated(EnumType.STRING)
    var status: Status?,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    var openedAt: LocalDateTime?,
    var nftCreator: String?
): BaseEntity() {
    @Transient
    var contents = mutableListOf<ArticleContent>()
    @Transient
    var hashtags = mutableListOf<Hashtag>()
}