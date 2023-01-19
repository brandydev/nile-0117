package com.example.nile0117.domain.entity

import com.example.nile0117.domain.base.BaseEntity
import com.example.nile0117.domain.enums.Status
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.LocalDateTime
import kotlin.jvm.Transient

@Entity
data class Article(
    var slug: String,
    @Enumerated(EnumType.STRING)
    var status: Status = Status.HIDDEN,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    var openedAt: LocalDateTime?,
    var creator: String? // article의 main nft 제작자
): BaseEntity() {
    @Transient
    var contents = mutableListOf<ArticleContent>()
}