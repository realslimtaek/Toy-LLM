package com.toy.LLM.adaptor.port

import com.toy.LLM.application.port.`in`.news.NewsUseCase
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/gemini")
class GeminiEndpoint(
    private val newsUseCase: NewsUseCase
) {


}