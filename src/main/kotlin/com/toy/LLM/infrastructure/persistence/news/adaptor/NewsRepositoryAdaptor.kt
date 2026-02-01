package com.toy.LLM.infrastructure.persistence.news.adaptor

import com.toy.LLM.application.port.out.news.NewsGetPort
import com.toy.LLM.application.port.out.news.NewsSavePort
import com.toy.LLM.infrastructure.persistence.news.repository.NewsRepository
import org.springframework.stereotype.Component

@Component
class NewsRepositoryAdaptor (
    private val repository: NewsRepository,
): NewsGetPort, NewsSavePort {

}