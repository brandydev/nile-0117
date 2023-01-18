package com.example.nile0117.domain.entity

import com.example.nile0117.domain.base.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "hashtag")
data class Hashtag(
    var text: String
): BaseEntity() {
}