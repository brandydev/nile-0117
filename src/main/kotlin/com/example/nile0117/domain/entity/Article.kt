package com.example.nile0117.domain.entity

import com.example.nile0117.domain.base.BaseEntity
import com.example.nile0117.domain.enums.Status
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import kotlin.jvm.Transient

@Serializable
@Entity
@Table(name = "article")
data class Article(
    var slug: String,
): BaseEntity() {

    @Enumerated(EnumType.STRING)
    var status: Status = Status.HIDDEN
    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd hh:mm:ss"
    )
    var openedAt: LocalDateTime? = null
    var creator: String? = null // article의 main nft 제작자

    @Transient
    var contents = mutableListOf<ArticleContent>()
}