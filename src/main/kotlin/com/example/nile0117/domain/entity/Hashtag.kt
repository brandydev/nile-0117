package com.example.nile0117.domain.entity

import com.example.nile0117.domain.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "hashtag")
data class Hashtag(
    var text: String
): BaseEntity() {
}