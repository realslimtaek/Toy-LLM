package com.toy.LLM.application.port.`in`.news

import com.toy.LLM.domain.StockInfo

interface NewsUseCase {
    fun getNews(query: String): StockInfo
}