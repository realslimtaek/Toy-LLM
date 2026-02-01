package com.toy.LLM.external.news

import com.toy.LLM.domain.NewsDataImpl
import org.springframework.stereotype.Component

@Component
object NewsMapper {

    fun toDomain(code: String, market: String, news: String) =
        NewsDataImpl(code, market, news)
}