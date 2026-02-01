package com.toy.LLM.application.port.out.news

import com.toy.LLM.domain.NewsData
import com.toy.LLM.domain.StockData

interface NewsPort {
    fun getNews(data: List<StockData>): List<NewsData>
}