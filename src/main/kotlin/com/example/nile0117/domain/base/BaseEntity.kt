package com.example.nile0117.domain.base

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.UUID

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    var id: UUID? = null

    @CreatedDate
    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd hh:mm:ss"
    )
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd hh:mm:ss"
    )
    var updatedAt: LocalDateTime? = null

    // createdBy, updatedBy >> spring security 기반 >> article에는 필요함.
    // 글 작성자와 글 등록자는 구분되어야 함.
    // article 기준으로 어떤 사람이 작성했는지 남기기 >> article entity에 columnist id 담기
    // 사용자 인증 체계가 필요함. token key를 활용해 audit 해야 함.
}