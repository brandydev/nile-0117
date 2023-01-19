package com.example.nile0117.domain.entity

import com.example.nile0117.domain.base.BaseEntity
import com.example.nile0117.domain.enums.Language
import jakarta.persistence.*
import java.util.*

@Entity
data class ArticleContent(
    @Enumerated(EnumType.STRING)
    var language: Language, // 필수
    var title: String?,
    var description: String?,
    var content: String?,
    var articleId: UUID? // 필수 >> 제일 먼저 주입 받도록
    // language와 article id를 복합 unique index 로 처리할 수 있음
    // 에러 처리 중요!
): BaseEntity() {
    // description >> 기본적으로 내용 넣지 않으면, 자동 생성되도록
}