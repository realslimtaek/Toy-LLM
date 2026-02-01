package com.toy.LLM.adaptor.port

import com.toy.LLM.application.port.`in`.news.NewsUseCase
import com.toy.LLM.application.port.out.gemini.GeminiPort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/gemini")
class GeminiEndpoint(
    private val newsUseCase: NewsUseCase,
    private val geminiPort: GeminiPort
) {

    @GetMapping("/test")
    fun test(
        query: String
    ) {
        newsUseCase.getNews(query)
        geminiPort.getStockName(query)
    }

    @GetMapping
    fun get(
        query: String
    ) {

//        geminiPort.getStockDescription(query)
    }


}