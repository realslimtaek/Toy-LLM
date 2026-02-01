package com.toy.LLM.application.service.news

import com.toy.LLM.application.port.`in`.news.NewsUseCase
import com.toy.LLM.application.port.out.gemini.GeminiPort
import com.toy.LLM.application.port.out.news.NewsPort
import com.toy.LLM.external.gemini.GeminiAdaptor
import org.springframework.stereotype.Service

@Service
class NewsService(
    private val geminiPort: GeminiPort,
    private val newsPort: NewsPort,
): NewsUseCase {

    override fun getNews(query: String) {

        val codes = geminiPort.getStockName(query)
        val news = newsPort.getNews(codes)
        val res = geminiPort.getStockDescription(news)
        res
    }
}