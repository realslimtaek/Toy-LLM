package com.toy.LLM.application.service.news

import com.toy.LLM.application.port.`in`.news.NewsUseCase
import com.toy.LLM.application.port.out.news.NewsGetPort
import com.toy.LLM.application.port.out.news.NewsSavePort
import org.springframework.stereotype.Service

@Service
class NewsService(
    private val newsGetPort: NewsGetPort,
    private val newsSavePort: NewsSavePort
): NewsUseCase {
}