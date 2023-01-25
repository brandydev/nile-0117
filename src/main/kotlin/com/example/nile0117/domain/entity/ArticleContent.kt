package com.example.nile0117.domain.entity

import com.example.nile0117.domain.base.BaseEntity
import com.example.nile0117.domain.enums.Language
import jakarta.persistence.*
import java.util.*

@Entity
data class ArticleContent( // language와 article id를 복합 unique index 로 처리할 수 있음
    var articleId: UUID?, // 필수
    @Enumerated(EnumType.STRING)
    var language: Language, // 필수
    var title: String?,
    var content: String?
): BaseEntity() {
    // description 처리
    // var description: String? = content?.slice(0..10) // 기본적으로 내용 넣지 않으면, 자동 생성되도록
}