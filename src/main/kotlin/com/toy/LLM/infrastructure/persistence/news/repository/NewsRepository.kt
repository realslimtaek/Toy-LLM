package com.toy.LLM.infrastructure.persistence.news.repository

import com.toy.LLM.infrastructure.persistence.news.entity.NewsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface NewsRepository : JpaRepository<NewsEntity, Long> {
}