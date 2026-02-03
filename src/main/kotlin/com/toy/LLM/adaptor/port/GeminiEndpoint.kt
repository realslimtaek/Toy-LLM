package com.toy.LLM.adaptor.port

import com.toy.LLM.adaptor.port.dto.ResponseMapper
import com.toy.LLM.adaptor.port.dto.StockInfoResponse
import com.toy.LLM.application.port.`in`.news.NewsUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/gemini")
class GeminiEndpoint(
    private val newsUseCase: NewsUseCase,
    private val responseMapper: ResponseMapper
) {

    @GetMapping("/test")
    fun test(
        query: String
    ): StockInfoResponse {
        return responseMapper.toResponse(newsUseCase.getNews(query))
    }
}
